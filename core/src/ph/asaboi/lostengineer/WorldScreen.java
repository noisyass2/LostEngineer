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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
    private TextButton btnMineCoal;
    private Table mineTable;
    private Table smeltTable;

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
        actionTable = GetActionsTable();
        mineTable = GetMineTable();
        smeltTable = GetSmeltTable();

        inventoryTable = Player.Global.Inventory.GetTable(skin);
        craftTable = Player.GetRecipesTable(skin);


        rootTable.add(inventoryTable).colspan(3).right();
        rootTable.row();

        rootTable.add(actionTable);
        rootTable.add(craftTable);
        rootTable.add(mineTable);
        rootTable.add(smeltTable);

        mineTable.setDebug(true);
        actionTable.setDebug(true);
        rootTable.setDebug(true);
        craftTable.setDebug(true);
    }

    private Table GetSmeltTable() {
        Table table = new Table();

        final Label lblIron = new Label("Iron Plate : " + Player.Global.Inventory.GetQuantity("iron-furnace"),skin);
        TextButton btnAddIronFurnace = new TextButton("+",skin);
        btnAddIronFurnace.addListener(new ChangeListener(){
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                Player.Global.AddFurnace("iron");
                lblIron.setText("Iron Plate : " + Player.Global.Inventory.GetQuantity("iron-furnace"));
            }
        });

        TextButton btnRemIronFurnace = new TextButton("-",skin);
        btnRemIronFurnace.addListener(new ChangeListener(){
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                Player.Global.DelFurnace("iron");
                lblIron.setText("Iron Plate : " + Player.Global.Inventory.GetQuantity("iron-furnace"));
            }
        });


        table.add(lblIron).pad(5);
        table.add(btnAddIronFurnace).size(30).pad(1);
        table.add(btnRemIronFurnace).size(30).pad(5);
        table.row();

        return table;
    }

    private Table GetMineTable() {
        Table table = new Table();
//
//        Label lblDrillCount = new Label("Drills:" + Player.Global.Inventory.GetQuantity("burner-mining-drill"),skin);
        final Label lblIron = new Label("Iron Ore : " + Player.Global.Inventory.GetQuantity("iron-drill"),skin);
        TextButton btnAddIronMiner = new TextButton("+",skin);
        btnAddIronMiner.addListener(new ChangeListener(){
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                Player.Global.AddMiner("iron");
                lblIron.setText("Iron Ore : " + Player.Global.Inventory.GetQuantity("iron-drill"));
            }
        });

        TextButton btnRemIronMiner = new TextButton("-",skin);
        btnRemIronMiner.addListener(new ChangeListener(){
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                Player.Global.DelMiner("iron");
                lblIron.setText("Iron Ore : " + Player.Global.Inventory.GetQuantity("iron-drill"));
            }
        });


        final Label lblCoal = new Label("Coal: " + Player.Global.Inventory.GetQuantity("coal-drill"),skin);
        TextButton btnAddCoalMiner = new TextButton("+",skin);
        btnAddCoalMiner.addListener(new ChangeListener(){
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                Player.Global.AddMiner("coal");
                lblCoal.setText("Coal: " + Player.Global.Inventory.GetQuantity("coal-drill"));
            }
        });

        TextButton btnRemCoalMiner = new TextButton("-",skin);
        btnRemCoalMiner.addListener(new ChangeListener(){
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                Player.Global.DelMiner("coal");
                lblCoal.setText("Coal: " + Player.Global.Inventory.GetQuantity("coal-drill"));
            }
        });


        final Label lblStone = new Label("Stone: " + Player.Global.Inventory.GetQuantity("stone-drill"),skin);
        TextButton btnAddStoneMiner = new TextButton("+",skin);
        btnAddStoneMiner.addListener(new ChangeListener(){
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                Player.Global.AddMiner("stone");
                lblStone.setText("Stone: " + Player.Global.Inventory.GetQuantity("stone-drill"));
            }
        });

        TextButton btnRemStoneMiner = new TextButton("-",skin);
        btnRemStoneMiner.addListener(new ChangeListener(){
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                Player.Global.DelMiner("stone");
                lblStone.setText("Stone: " + Player.Global.Inventory.GetQuantity("stone-drill"));
            }
        });

//        table.add(lblDrillCount).colspan(3).right();
//        table.row();
        table.add(lblIron).pad(5);
        table.add(btnAddIronMiner).size(30).pad(1);
        table.add(btnRemIronMiner).size(30).pad(5);
        table.row();
        table.add(lblCoal).pad(5);
        table.add(btnAddCoalMiner).size(30).pad(1);
        table.add(btnRemCoalMiner).size(30).pad(5);
        table.row();
        table.add(lblStone).pad(5);
        table.add(btnAddStoneMiner).size(30).pad(1);
        table.add(btnRemStoneMiner).size(30).pad(5);
        table.row();

        return  table;
    }

    private Table GetActionsTable() {
        Table table = new Table();
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
                Player.Mine("iron-ore");
            }
        });

        btnMineCopper = new TextButton("Mine Copper",skin);
        btnMineCopper.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Player.Mine("copper-ore");
            }
        });

        btnMineStone = new TextButton("Mine Stone",skin);
        btnMineStone.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Player.Mine("stone");
            }
        });

        btnMineCoal = new TextButton("Mine Coal",skin);
        btnMineCoal.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Player.Mine("coal");
            }
        });

        //ProgressBar
        pBarChop = new ProgressBar(0,100,1,false,skin);
        pBarChop.setValue(100);
        pBarChop.setWidth(50);

        table.add(pBarChop).width(100).height(5).pad(5);
        table.row();
        table.add(btnChop).width(100).pad(5);
        table.row();
        table.add(btnMineStone).width(100).pad(5);
        table.row();
        table.add(btnMineCoal).width(100).pad(5);
        table.row();
        table.add(btnMineIron).width(100).pad(5);
        table.row();
        table.add(btnMineCopper).width(100).pad(5);
        table.row();

        return  table;
    }


}
