import org.json.simple.*;

import java.util.ArrayList;
import java.util.List;

public class MessageListResponseClass extends RequestClass{

    List<MessageClass> messageList;

    public MessageListResponseClass(List <MessageClass> newMessageList)
    {
        _class = "MessageListResponse";
        messageList = newMessageList;
    }


    @Override
    public JSONObject newJSONObj()
    {
        List<JSONObject> jsonObj = new ArrayList<JSONObject>();

        for (MessageClass newMsg : messageList)
        {
            jsonObj.add(newMsg.newJSONObj());
        }

        JSONObject newObject = new JSONObject();
        newObject.put("_class", _class);
        newObject.put("messages", jsonObj);
        return newObject;
    }



}
