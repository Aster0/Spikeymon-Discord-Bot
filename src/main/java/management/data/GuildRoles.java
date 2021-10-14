package management.data;

public class GuildRoles {

    public long getYear1Role() {
        return year1Role;
    }

    public void setYear1Role(long year1Role) {
        this.year1Role = year1Role;
    }

    public long getYear2Role() {
        return year2Role;
    }

    public void setYear2Role(long year2Role) {
        this.year2Role = year2Role;
    }

    public long getYear3Role() {
        return year3Role;
    }

    public void setYear3Role(long year3Role) {
        this.year3Role = year3Role;
    }

    private long year1Role;
    private long year2Role;
    private long year3Role;

    public long getVerifiedRole() {
        return verifiedRole;
    }

    public void setVerifiedRole(long verifiedRole) {
        this.verifiedRole = verifiedRole;
    }

    private long verifiedRole;
}
