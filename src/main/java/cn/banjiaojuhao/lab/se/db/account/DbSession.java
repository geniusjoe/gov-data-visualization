package cn.banjiaojuhao.lab.se.db.account;


import cn.banjiaojuhao.lab.se.db.middleware.OPSession;
import cn.banjiaojuhao.lab.se.dbinterface.ISession;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DbSession implements ISession {

    @NotNull
    @Override
    public String createSession(int uid) {
        OPSession ses = new OPSession();
        return ses.createSession(uid);
    }

    @Nullable
    @Override
    public Integer querySession(@NotNull String sid) {
        OPSession ses = new OPSession();
        return ses.querySession(sid);
    }

    @Override
    public void deleteSession(@NotNull String sid) {
        OPSession ses = new OPSession();
        ses.deleteSession(sid);
    }
}
