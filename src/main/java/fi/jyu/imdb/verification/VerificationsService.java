package fi.jyu.imdb.verification;

import java.util.*;

public class VerificationsService {
    private HashMap<String, Verification> verificationHashMap;
    private static VerificationsService instance;

    private VerificationsService() {
        this.verificationHashMap = new HashMap<String, Verification>();
    }

    public synchronized static VerificationsService getInstance(){
        if(instance == null){
            instance = new VerificationsService();
        }

        return instance;
    }

    public ArrayList<Verification> getVerificationsList() {
        return new ArrayList<Verification>(verificationHashMap.values());
    }

    public Verification getVerificationByHash(String hash) {
        return verificationHashMap.get(hash);
    }

    public Verification getVerificationByUserLogin(String login) {
        for (Verification verification : getVerificationsList()) {
            if (verification.getUser().getLogin().equals(login)) {
                return verification;
            }
        }
        return null;
    }

    public synchronized Verification addVerification(Verification verification) {
        verificationHashMap.put(verification.getHash(), verification);
        return verification;
    }

    public synchronized void removeVerification(String hash) {
        verificationHashMap.remove(hash);
    }
}
