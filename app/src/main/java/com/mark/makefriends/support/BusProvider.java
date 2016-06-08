package com.mark.makefriends.support;
import com.mark.makefriends.support.otto.Bus;

/**
 * Created by xu on 15/11/17.
 */
public class BusProvider {
    private static final BusProvider instance = new BusProvider();
    //otto or eventbus
    private Bus BUS = new Bus();

    public static BusProvider getInstance() {
        return instance;
    }
    private BusProvider() {
        // No instances.
    }
    public void regist(Object object){
        BUS.register(object);
    }
    public void unregist(Object object){
        BUS.unregister(object);
    }
    public void post(final Object object){
        BUS.post(object);
    }
}
