package interbase.interclient;

import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;

public class XAResource implements javax.transaction.xa.XAResource {
   public static final int TMENDRSCAN = 8388608;
   public static final int TMFAIL = 536870912;
   public static final int TMJOIN = 2097152;
   public static final int TMNOFLAG = 0;
   public static final int TMONEPHASE = 1073741824;
   public static final int TMRESUME = 134217728;
   public static final int TMSTARTRSCAN = 16777216;
   public static final int TMSUCCESS = 67108864;
   public static final int TMSUSPEND = 33554432;

   public synchronized void commit(Xid var1, boolean var2) throws XAException {
   }

   public synchronized void end(Xid var1, int var2) throws XAException {
   }

   public synchronized void forget(Xid var1) throws XAException {
   }

   public synchronized int getTransactionTimeout() throws XAException {
      return 0;
   }

   public synchronized int prepare(Xid var1) throws XAException {
      return 0;
   }

   public synchronized Xid[] recover(int var1) throws XAException {
      return null;
   }

   public synchronized void rollback(Xid var1) throws XAException {
   }

   public synchronized boolean setTransactionTimeout(int var1) throws XAException {
      return false;
   }

   public synchronized void start(Xid var1, int var2) throws XAException {
   }

   public synchronized boolean isSameRM(javax.transaction.xa.XAResource var1) throws XAException {
      return false;
   }
}
