package ph.asaboi.lostengineer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import ph.asaboi.lostengineer.classes.Global;
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
    private TextButton btnMine;
    private ProgressBar pBarMine;
    private Player Player;

    public WorldScreen(final LostEngineerGame game) {
        this.game = game;

        skin = new Skin(Gdx.files.internal("ui/level-plane-ui.json"));
        skind = new Skin(Gdx.files.internal("data/uiskin.json"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        //Table
        CreateTable();

        rootTable.setFillParent(true);
        stage.addActor(rootTable);
        Player = new Player(new Global());
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);

//        pBarMine.act(delta);
        Player.Update();
        pBarMine.setValue(Player.ChopPBARValue);
        btnMine.setTouchable(Player.IsChopEnabled ? Touchable.enabled : Touchable.disabled);
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

        // Buttons
        btnMine = new TextButton("Mine",skin);
        btnMine.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Player.Chop();
            }
        });

        //ProgressBar
        pBarMine = new ProgressBar(0,100,1,false,skin);
        pBarMine.setValue(100);
        pBarMine.setWidth(50);


        rootTable.add(pBarMine).width(100).height(5);
        rootTable.row();
        rootTable.add(btnMine).width(100);
        rootTable.row();
//        rootTable.debug();
    }


}
