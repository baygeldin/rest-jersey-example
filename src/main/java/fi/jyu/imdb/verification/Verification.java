package fi.jyu.imdb.verification;

import fi.jyu.imdb.user.User;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@XmlRootElement
public class Verification {
    private String hash;
    private User user;

    public Verification() {}

    public Verification(User user) {
        this.user = user;
        this.hash = UUID.randomUUID().toString();
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
