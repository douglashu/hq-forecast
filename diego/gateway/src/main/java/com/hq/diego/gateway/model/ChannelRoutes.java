package com.hq.diego.gateway.model;

import com.hq.diego.repository.model.generate.TRudyChannelRoute;
import java.util.List;

/**
 * Created by zhaoyang on 14/03/2017.
 */
public class ChannelRoutes {
    private List<TRudyChannelRoute> routes;

    public List<TRudyChannelRoute> getRoutes() {
        return routes;
    }

    public void setRoutes(List<TRudyChannelRoute> routes) {
        this.routes = routes;
    }
}
