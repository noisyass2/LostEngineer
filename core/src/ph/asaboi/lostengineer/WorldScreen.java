package ph.asaboi.lostengineer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import ph.asaboi.lostengineer.classes.Global;
import ph.asaboi.lostengineer.classes.Inventory;
import ph.asaboi.lostengineer.classes.Item;
import ph.asaboi.lostengineer.classes.Player;

/**
 * Created by P004785 on 1/24/2017.
 */
public class WorldScreen implements Screen {
    private final LostEngineerGame game;
    private final Skin skin;
    private final Skin skind;
    private final Stage stage;
    Table rootTable;
    private TextButton btnMineCopper;
    private ProgressBar pBarMineCopper;
    private TextButton btnMineIron;
    private TextButton btnMineStone;
    private ProgressBar pBarMineIron;
    private TextButton btnChop;
    private ProgressBar pBarChop;

    private Player Player;
    private Table actionTable;
    private Table inventoryTable;
    private Table craftTable;

    public WorldScreen(final LostEngineerGame game) {
        this.game = game;

        skin = new Skin(Gdx.files.internal("ui/level-plane-ui.json"));
        skind = new Skin(Gdx.files.internal("data/uiskin.json"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Player = new Player(new Global());
        //Table
        CreateTable();

        rootTable.setFillParent(true);
        stage.addActor(rootTable);

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);

//        pBarMineIron.act(delta);
        Player.Update();
        Player.Global.Inventory.Update();
//        pBarMineIron.setValue(Player.MinePBARValue);


        pBarChop.setValue(Player.ActionBARValue);

        Touchable actionEnabled = !Player.IsBusy ? Touchable.enabled : Touchable.disabled;
        btnChop.setTouchable(actionEnabled );
        btnMineIron.setTouchable(actionEnabled);
        btnMineCopper.setTouchable(actionEnabled);
        btnMineStone.setTouchable(actionEnabled);

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        skin.dispose();
        stage.dispose();
    }


    private void CreateTable() {
        rootTable = new Table();
        actionTable = new Table();
        // Buttons
        btnChop = new TextButton("Chop",skin);
        btnChop.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Player.Chop();
            }
        });

        btnMineIron = new TextButton("Mine Iron",skin);
        btnMineIron.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Player.Mine("IronOre");
            }
        });

        btnMineCopper = new TextButton("Mine Copper",skin);
        btnMineCopper.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Player.Mine("CopperOre");
            }
        });

        btnMineStone = new TextButton("Mine Stone",skin);
        btnMineStone.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Player.Mine("Stone");
            }
        });

        //ProgressBar
        pBarChop = new ProgressBar(0,100,1,false,skin);
        pBarChop.setValue(100);
        pBarChop.setWidth(50);

        actionTable.add(pBarChop).width(100).height(5).pad(5);
        actionTable.row();
        actionTable.add(btnChop).width(100).pad(5);
        actionTable.row();
        actionTable.add(btnMineIron).width(100).pad(5);
        actionTable.row();
        actionTable.add(btnMineCopper).width(100).pad(5);
        actionTable.row();
        actionTable.add(btnMineStone).width(100).pad(5);
        actionTable.row();



        inventoryTable = Player.Global.Inventory.GetTable(skin);
        craftTable = Player.GetRecipesTable(skin);

        rootTable.add(actionTable);
        rootTable.add(inventoryTable);
        rootTable.row();
        rootTable.add(craftTable);

        actionTable.setDebug(true);
        rootTable.setDebug(true);
        craftTable.setDebug(true);
    }


}
