package ph.asaboi.lostengineer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

import ph.asaboi.lostengineer.classes.Global;
import ph.asaboi.lostengineer.classes.Player;
import ph.asaboi.lostengineer.classes.ProgressButton;

/**
 * Created by benad on 10/12/17.
 */

public class PlayGameScreen implements Screen {

    private final LostEngineerGame game;
    private final Skin skin;
    private final Skin skind;
    private final Stage stage;
    Table rootTable;
    private Player Player;
    private ProgressButton pButton;
    private ProgressButton pButton2;

    public PlayGameScreen(final LostEngineerGame game){
        this.game = game;
        skin = new Skin(Gdx.files.internal("ui/level-plane-ui.json"));
        skind = new Skin(Gdx.files.internal("data/uiskin.json"));
        stage = new Stage(new FitViewport(480,800));
        Gdx.input.setInputProcessor(stage);

        Player = new Player(new Global());
        //Table
        CreateTable();

        rootTable.setFillParent(true);
        stage.addActor(rootTable);
    }

    private void CreateTable() {
        rootTable = new Table();

        rootTable.setFillParent(true);
        final ArrayList<Table> tables = new ArrayList<Table>();
        Table craftTable = GetCraftTable();
        Table mineTable = GetMineTable();
        Table smeltTable = GetSmeltTable();
        Table assembleTable = GetAssembleTable();
        Table researchTable = GetResearchTable();

        tables.add(craftTable);
        tables.add(mineTable);
        tables.add(smeltTable);
        tables.add(assembleTable);
        tables.add(researchTable);
        Stack container = new Stack();
        for (Table tbl :
                tables) {
            container.add(tbl);
        }


        final HorizontalGroup group = new HorizontalGroup();
        ButtonGroup btabs = new ButtonGroup();
        btabs.setMinCheckCount(1);
        btabs.setMaxCheckCount(1);

        final String[] tabs = new String [] { "Craft","Mine", "Smelt","Assemble","Research" };



        for (String tab :
                tabs) {

            final Button tabCmd = new TextButton(tab, skin);
            tabCmd.setName(tab);
            group.addActor(tabCmd);
            btabs.add(tabCmd);
        }

        ChangeListener tab_listener = new ChangeListener(){
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                for (int i = 0; i < 5; i++) {
                    tables.get(i).setVisible(((Button)group.findActor(tabs[i])).isChecked());
                }
            }
        };

        for (String tab :
                tabs) {
            TextButton tabCmd = group.findActor(tab);
            tabCmd.addListener(tab_listener);
        }

        Container itemContainer = new Container();
        rootTable.row().height(250);
        rootTable.add(itemContainer).fill();
        rootTable.row().height(50);
        rootTable.add(group);
        rootTable.row();
        rootTable.add(container).fill();

//        rootTable.setDebug(true);
//        container.setDebug(true);
    }

    private Table GetResearchTable() {
        return new Table();
    }

    private Table GetAssembleTable() {
        return new Table();
    }

    private Table GetSmeltTable() {
        return new Table();
    }

    private Table GetMineTable() {
        return new Table();
    }

    private Table GetCraftTable() {
        Table tbl =  new Table();

        pButton = new ProgressButton("Iron ore","iron-ore",this.Player,this.skin);
        tbl.add(pButton).fillX();
        tbl.row();

        pButton2 = new ProgressButton("Copper ore","copper-ore",this.Player,this.skin);
        tbl.add(pButton2).fillX();

        ScrollPane pane = new ScrollPane(tbl);
        Table table = new Table();
        table.add(pane).fill().expand();
        return  table;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        pButton.Update();
        pButton2.Update();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
