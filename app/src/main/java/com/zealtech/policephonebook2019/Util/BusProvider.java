package com.zealtech.policephonebook2019.Util;

import com.squareup.otto.Bus;

public final class BusProvider {

    private static Bus bus;

    public static Bus getInstance() {
        if (bus == null) {
            bus = new Bus();
        }

        return bus;
    }
}
