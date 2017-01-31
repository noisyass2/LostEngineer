package ph.asaboi.lostengineer.classes;

import org.omg.CORBA.Current;

/**
 * Created by P004785 on 1/26/2017.
 */

public class Player {
    public float ChopSpeed;
    public boolean IsChopping;
    private int ChopCounter;
    public boolean IsChopEnabled;
    public float ChopPBARValue;
    public boolean IsMining;
    public int MineCounter;
    public int MineSpeed;
    public int MinePBARValue;
    public Global Global;
    public boolean IsMineEnabled;
    public String CurrentOre;

    public boolean IsBusy;
    public float ActionBARValue;
    public float ActionCounter;
    public float ActionSpeed;

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

        ActionCounter = 0;
        ActionBARValue = 0;
        ActionSpeed = 1;
    }



    public void Update()
    {

//        if (IsChopping)
//        {
//            ChopCounter -= ChopSpeed;
//            ChopPBARValue = (ChopCounter <= 0) ? 0 : ChopCounter;
//            if(ChopCounter <= 0)
//            {
//                if (IsChopping)
//                {
//                    IsChopping = false;
//                    IsChopEnabled = true;
//                    Global.GainWood(10);
//                }
//            }
//        }
//
//        if (IsMining)
//        {
//            MineCounter -= MineSpeed;
//            MinePBARValue = (MineCounter < 0) ? 0 : MineCounter;
//            if(MinePBARValue <= 0)
//            {
//                if (IsMining)
//                {
//                    IsMining = false;
//                    IsMineEnabled = true;
//                    Global.GainOre(CurrentOre,1);
//                }
//            }
//        }

        if(IsBusy)
        {
            ActionCounter -= ActionSpeed;
            ActionBARValue = (ActionCounter< 0) ? 0 : ActionCounter;
            if(ActionBARValue <= 0)
            {
                if(IsChopping)
                {
                    IsChopping = false;
                    Global.GainWood(10);
                }

                if(IsMining)
                {
                    IsMining = false;
                    Global.GainOre(CurrentOre,1);
                }

                IsBusy = false;
            }
        }
    }

    public void Chop(){
        IsBusy = true;
        ActionCounter += 100;
        IsChopping = true;
    }

    public void Mine(String ore) {
        CurrentOre = ore;
        IsBusy = true;
        ActionCounter += 100;
        IsMining = true;
    }
}
