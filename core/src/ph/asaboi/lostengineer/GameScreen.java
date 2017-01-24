package ph.asaboi.lostengineer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by P004785 on 1/24/2017.
 */
public class GameScreen implements Screen {
    final LostEngineerGame game;
    private final Skin skin;
    private Table table;


    private Stage stage;
    OrthographicCamera camera;

    public GameScreen(final LostEngineerGame game) {

        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        stage = new Stage(new ScreenViewport());

        Table main = new Table();
        main.setFillParent(true);

        // Create the tab buttons
        HorizontalGroup group = new HorizontalGroup();
        final Button tab1 = new TextButton("Tab1", skin, "toggle");
        final Button tab2 = new TextButton("Tab2", skin, "toggle");
        final Button tab3 = new TextButton("Tab3", skin, "toggle");
        group.addActor(tab1);
        group.addActor(tab2);
        group.addActor(tab3);
        main.add(group);
        main.row();

        // Create the tab content. Just using images here for simplicity.
        Stack content = new Stack();
        final Stack tabcontent1 = new Stack();
//        final Image content1 = new Image(skin.newDrawable("white", 1,0,0,1));
        final Image content2 = new Image(skin.newDrawable("white", 0,1,0,1));
        final Image content3 = new Image(skin.newDrawable("white", 0,0,1,1));
        final Button craftButton = new TextButton("Craft", skin, "toggle");
        final Button mineButton = new TextButton("Mine", skin, "toggle");

        craftButton.setHeight(50);
        mineButton.setHeight(50);
        tabcontent1.add(craftButton);
        tabcontent1.add(mineButton);

        content.addActor(tabcontent1);
        content.addActor(content2);
        content.addActor(content3);

        main.add(content).expand().fill();

        // Listen to changes in the tab button checked states
        // Set visibility of the tab content to match the checked state
        ChangeListener tab_listener = new ChangeListener(){
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                tabcontent1.setVisible(tab1.isChecked());
                content2.setVisible(tab2.isChecked());
                content3.setVisible(tab3.isChecked());
            }
        };
        tab1.addListener(tab_listener);
        tab2.addListener(tab_listener);
        tab3.addListener(tab_listener);

        // Let only one tab button be checked at a time
        ButtonGroup tabs = new ButtonGroup();
        tabs.setMinCheckCount(1);
        tabs.setMaxCheckCount(1);
        tabs.add(tab1);
        tabs.add(tab2);
        tabs.add(tab3);

        stage.addActor(main);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
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
        stage.dispose();
        skin.dispose();
    }
}
