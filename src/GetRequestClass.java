import org.json.simple.*;

public class GetRequestClass extends RequestClass{

    private String identity = "";
    private long after = 0;

    public GetRequestClass(String newIdentity, long newAfter)
    {
        _class = "GetRequest";
        identity = newIdentity;
        after = newAfter;
    }

    @Override
    public JSONObject newJSONObj()
    {
        JSONObject newObject = new JSONObject();
        newObject.put("_class", _class);
        newObject.put("identity", identity);
        newObject.put("after", after);
        return newObject;
    }

    public String getIdentity()
    {
        return identity;
    }

    public float getAfter()
    {
        return after;
    }
}
