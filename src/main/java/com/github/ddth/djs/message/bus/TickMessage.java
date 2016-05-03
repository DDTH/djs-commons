package com.github.ddth.djs.message.bus;

import com.github.ddth.djs.message.BaseMessage;

/**
 * This message is sent per "tick".
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public class TickMessage extends BaseMessage {
    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     * 
     * @since 0.1.3
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof TickMessage && super.equals(obj);
    }

    /*----------------------------------------------------------------------*/
    public static void main(String[] args) {
        TickMessage orgMsg = new TickMessage();
        System.out.println(orgMsg);
        {
            String dataJson = orgMsg.toJson();
            System.out.println(dataJson.getBytes().length + "\t" + dataJson);
            TickMessage msg = deserialize(dataJson, TickMessage.class);
            System.out.println(msg);
        }
        {
            byte[] bytes = orgMsg.toBytes();
            System.out.println(bytes.length + "\t" + bytes);
            TickMessage msg1 = deserialize(bytes, TickMessage.class);
            System.out.println(msg1);
            BaseMessage msg2 = BaseMessage.deserialize(bytes);
            System.out.println(msg2);
        }
    }
}
