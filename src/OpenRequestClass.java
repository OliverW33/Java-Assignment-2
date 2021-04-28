import org.json.simple.*;

public class OpenRequestClass extends RequestClass{

    String identity= "";

    public OpenRequestClass(String newIdentity)
    {
        _class = "OpenRequest";
        identity = newIdentity;
    }

    @Override
    public JSONObject newJSONObj()
    {
        JSONObject newObject = new JSONObject();
        newObject.put("_class", _class);
        newObject.put("identity", identity);
        return newObject;
    }

    public String getIdentity()
    {
        return identity;
    }

}
