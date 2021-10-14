package data.session;

import data.GDDUser;
import net.dv8tion.jda.api.entities.User;

public class VerificationSession {


    public GDDUser getGddUser() {
        return gddUser;
    }

    public void setGddUser(GDDUser gddUser) {
        this.gddUser = gddUser;
    }

    private GDDUser gddUser;

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    private long requestId;


}
