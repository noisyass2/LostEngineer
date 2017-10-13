package ph.asaboi.lostengineer.classes;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Logger;

/**
 * Created by benad on 10/12/17.
 */

public class ProgressButton extends Table{

    private final String DisplayText;
    private final String ItemCode;
    private final Logger logger;
    private final Player Player;
    private float ActionCounter;
    private float ActionBARValue;
    private final float ActionSpeed;
    private float ActionMax;
    private Label invLabel;
    private ProgressBar pBar;
    private Global Global;
    private boolean IsBusy;

    public ProgressButton(String displayText,String itemCode,Player player, Skin skin){
        this.DisplayText = displayText;
        this.ItemCode = itemCode;
        this.Player = player;
        this.setSkin(skin);
        this.logger = new Logger("APPLOGS");

        ActionCounter = 0;
        ActionBARValue = 0;
        ActionSpeed = 1;
        ActionMax = 100;

        Global = Global.getInstance();

        CreateTable();
    }

    private void CreateTable() {
        Table leftTbl = new Table();
        Table rightTbl = new Table();
        invLabel = new Label("0",this.getSkin());
        invLabel.setFontScale(1.5f);
        pBar = new ProgressBar(0,100,1,false,getSkin());
        leftTbl.add(new Label(this.DisplayText,this.getSkin())).width(100).expand().colspan(1);
        leftTbl.add(invLabel).width(100).right();
        leftTbl.row();

        rightTbl.add(new Label("Qty",this.getSkin())).width(100);
        rightTbl.add(new TextButton("-",this.getSkin())).width(40);
        rightTbl.add(new TextButton("+",this.getSkin())).width(40);


        leftTbl.add(new Label("+0",this.getSkin()));
        leftTbl.add(new Label("-0",this.getSkin()));
        leftTbl.setTouchable(Touchable.enabled);
        leftTbl.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("tbl clicked.");
//                Player.Mine(ItemCode);
                if(!IsBusy) {
                    startMine(ItemCode);
                }
            }
        });
        add(leftTbl).expandX();
        add(rightTbl);
        row();
        add(pBar).expand().colspan(2).width(400);
//        setDebug(true);
    }

    private void startMine(String itemCode) {
        IsBusy = true;
        ActionCounter += 100;
        ActionMax = 100;
    }

    public void Update()
    {
        //Player.Update();
        int qty = Global.Inventory.GetQuantity(ItemCode);
        invLabel.setText(qty + "");
        if(IsBusy)
        {
            ActionCounter -= ActionSpeed;
            ActionBARValue = (((ActionCounter/ActionMax  ) * 100) < 0) ? 0 : (ActionCounter/ActionMax ) * 100;
//            System.out.println("ActionBARValue = " + ActionBARValue);
            if(ActionBARValue <= 0)
            {
                Global.GainOre(ItemCode,1);
                IsBusy = false;
            }
        }
        pBar.setValue(100 - ActionBARValue);
    }

}
