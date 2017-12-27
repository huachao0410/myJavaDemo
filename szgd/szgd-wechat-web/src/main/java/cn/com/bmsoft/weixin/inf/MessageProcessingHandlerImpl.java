package cn.com.bmsoft.weixin.inf;

import cn.com.bmsoft.weixin.bean.InMessage;
import cn.com.bmsoft.weixin.bean.OutMessage;
import org.springframework.stereotype.Service;

/**
 * Created by daniel on 2017/5/19.
 */
@Service
public class MessageProcessingHandlerImpl implements MessageProcessingHandler {
    @Override
    public void allType(InMessage msg) {

    }

    @Override
    public void textTypeMsg(InMessage msg) {

    }

    @Override
    public void locationTypeMsg(InMessage msg) {

    }

    @Override
    public void imageTypeMsg(InMessage msg) {

    }

    @Override
    public void videoTypeMsg(InMessage msg) {

    }

    @Override
    public void linkTypeMsg(InMessage msg) {

    }

    @Override
    public void voiceTypeMsg(InMessage msg) {

    }

    @Override
    public void verifyTypeMsg(InMessage msg) {

    }

    @Override
    public void eventTypeMsg(InMessage msg) {

    }

    @Override
    public void afterProcess(InMessage inMsg, OutMessage outMsg) {

    }

    @Override
    public void setOutMessage(OutMessage outMessage) {

    }

    @Override
    public OutMessage getOutMessage() {
        return null;
    }
}
