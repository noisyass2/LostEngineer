package ph.asaboi.lostengineer.classes;

/**
 * Created by P004785 on 1/26/2017.
 */

public class Player {
    public float ChopSpeed;
    public boolean IsChopping;
    private int ChopCounter;
    public boolean IsChopEnabled;
    public float ChopPBARValue;
    private boolean IsMining;
    private int MineCounter;
    private int MineSpeed;
    private int MinePBARValue;
    private Global Global;
    private boolean IsMineEnabled;
    private String CurrentOre;

    public Player(Global global) {
        IsChopping = false;
        ChopSpeed = 1;
        MineSpeed = 1;
        ChopCounter = 0;
        IsChopEnabled = true;
        Global = global;
        IsMining = false;
        MineCounter = 0;
        IsMineEnabled = true;
    }

    public void Chop(){
        IsChopping = true;
        ChopCounter += 100;
        IsChopEnabled = false;
    }

    public void Update()
    {
        if (IsChopping)
        {
            ChopCounter -= ChopSpeed;
            ChopPBARValue = (ChopCounter <= 0) ? 0 : ChopCounter;
            if(ChopCounter <= 0)
            {
                if (IsChopping)
                {
                    IsChopping = false;
                    IsChopEnabled = true;
                    Global.GainWood(10);
                }
            }
        }

        if (IsMining)
        {
            MineCounter -= MineSpeed;
            MinePBARValue = (MineCounter < 0) ? 0 : MineCounter;
            if(MinePBARValue <= 0)
            {
                if (IsMining)
                {
                    IsMining = false;
                    IsMineEnabled = true;
                    Global.GainOre(CurrentOre,1);
                }
            }
        }
    }
}
