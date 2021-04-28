import java.util.ArrayList;
import java.util.List;

public class ChannelClass{

    private String identity = "";
    private List<MessageClass> userMessages = new ArrayList<MessageClass>();
    private List<String> subbedUsers = new ArrayList<String>();

    public ChannelClass (String newIdentity)
    {
        identity = newIdentity;
    }

    public ChannelClass (String newIdentity, List<MessageClass> newUserMessages, List<String> newSubscriber)
    {
        identity = newIdentity;
        userMessages = newUserMessages;
        subbedUsers = newSubscriber;
    }

    public String getIdentity()
    {
        return identity;
    }

    public List<MessageClass> getUserMessages()
    {
        return userMessages;
    }

    public List<String> getSubs()
    {
        return subbedUsers;
    }

    public void addMsg(MessageClass newMessage)
    {
        userMessages.add(newMessage);
    }

    public boolean findSub(String newIdentity)
    {
        for (String subbedIdentity : subbedUsers)
        {
            if(subbedIdentity.equals(newIdentity))
            {
                return true;
            }
        }
        return false;
    }

    public boolean addSub(String newIdentity)
    {
        if (!findSub(newIdentity))
        {
            subbedUsers.add(newIdentity);
            return true;
        }
        return false;
    }

    public boolean removeSub(String newIdentity)
    {
        for(String loopedIdentity : subbedUsers)
        {
            if (newIdentity.equals(loopedIdentity))
            {
                subbedUsers.remove(loopedIdentity);
                return true;
            }
        }
        return false;
    }




}
