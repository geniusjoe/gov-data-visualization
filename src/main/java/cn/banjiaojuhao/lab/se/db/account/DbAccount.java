package cn.banjiaojuhao.lab.se.db.account;

import cn.banjiaojuhao.lab.se.account.User;
import cn.banjiaojuhao.lab.se.account.UserProfile;
import cn.banjiaojuhao.lab.se.db.middleware.OPUser;
import cn.banjiaojuhao.lab.se.dbinterface.IAccount;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DbAccount implements IAccount {

    @Override
    public User queryUserByName(@NotNull String username) {
        OPUser usr = new OPUser();
        return usr.queryUserbyName(username);
    }

    @Nullable
    @Override
    public User queryUserByUid(int uid) {
        OPUser usr = new OPUser();
        return usr.queryUserByUid(uid);
    }

    @Override
    public void changePassword(int uid, @NotNull String new_password) {
        OPUser usr = new OPUser();
        usr.changePassword(uid,new_password);
    }

    @Override
    public void updateUserProfile(@NotNull UserProfile new_profile) {
        OPUser usr = new OPUser();
        usr.updateUserProfile(new_profile);
    }

    @Nullable
    @Override
    public UserProfile queryUserProfile(int uid) {
        OPUser usr = new OPUser();
        return usr.queryUserProfile(uid);
    }
}
