package com.bramble.kickback.networking;

public enum ClientPacketType {
    PACKET_0_KEEP_ALIVE(0),
    PACKET_1_LOGIN(1),
    PACKET_2_UPDATE_STATUS(2),
    PACKET_3_FETCH_FRIENDS(3),
    PACKET_4_ADD_SCHEDULE_ITEM(4),
    PACKET_5_UPDATE_SCHEDULE_ITEM(5),
    PACKET_6_REGISTER_ACCOUNT(6);

    private final int value;

    ClientPacketType(final int i) {
        value = i;
    }

    public int getValue() {
        return value;
    }


}
