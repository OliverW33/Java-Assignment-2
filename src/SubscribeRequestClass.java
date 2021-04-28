import org.json.simple.*;

public class SubscribeRequestClass extends RequestClass {

    private String identity = "";
    private String subscribedChannel = "";

    public SubscribeRequestClass(String newIdentity, String newSubscribedChannel)
    {
        _class = "SubscribeRequest";
        identity = newIdentity;
        subscribedChannel = newSubscribedChannel;
    }

    @Override
    public JSONObject newJSONObj()
    {
        JSONObject newObject = new JSONObject();
        newObject.put("_class", _class);
        newObject.put("identity", identity);
        newObject.put("channel", subscribedChannel);
        return newObject;
    }

    public String getSubscribedChannel()
    {
        return subscribedChannel;
    }

    public String getIdentity()
    {
        return identity;
    }

}
