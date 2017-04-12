package com.hq.diego.gateway.model;

import com.hq.diego.repository.model.generate.TRudyMchChannel;

import java.util.List;

/**
 * Created by zhaoyang on 15/03/2017.
 */
public class MchChannels {
    private List<TRudyMchChannel> channels;

    public List<TRudyMchChannel> getChannels() {
        return channels;
    }

    public void setChannels(List<TRudyMchChannel> channels) {
        this.channels = channels;
    }
}
