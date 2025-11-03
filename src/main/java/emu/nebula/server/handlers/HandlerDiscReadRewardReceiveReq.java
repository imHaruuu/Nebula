package emu.nebula.server.handlers;

import emu.nebula.net.NetHandler;
import emu.nebula.net.NetMsgId;
import emu.nebula.proto.DiscReadRewardReceive.DiscReadRewardReceiveReq;
import emu.nebula.net.HandlerId;
import emu.nebula.net.GameSession;

@HandlerId(NetMsgId.disc_read_reward_receive_req)
public class HandlerDiscReadRewardReceiveReq extends NetHandler {

    @Override
    public byte[] handle(GameSession session, byte[] message) throws Exception {
        // Parse request
        var req = DiscReadRewardReceiveReq.parseFrom(message);
        
        // Get disc
        var disc = session.getPlayer().getCharacters().getDiscById(req.getId());
        if (disc == null) {
            return session.encodeMsg(NetMsgId.disc_read_reward_receive_failed_ack);
        }
        
        // Set read reward
        var change = disc.receiveReadReward();
        if (change == null) {
            return session.encodeMsg(NetMsgId.disc_read_reward_receive_failed_ack);
        }
        
        // Encode and send
        return session.encodeMsg(NetMsgId.disc_read_reward_receive_succeed_ack, change.toProto());
    }

}
