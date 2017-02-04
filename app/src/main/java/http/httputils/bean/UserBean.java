package http.httputils.bean;

/**
 * Created by HDL on 2016/10/18.
 */

import java.io.Serializable;
import java.util.List;

/**
 * Created by HDL on 2016/9/1.
 */
public class UserBean implements Serializable{
    /**
     * errno : 0
     * error : success
     * data : [{"departid":"1","departname":"果布戛","head":"李一","address":""},{"departid":"2","departname":"派出所","head":"李二","address":""},{"departid":"3","departname":"运管所","head":"李三","address":""},{"departid":"4","departname":"检查院","head":"李四","address":""},{"departid":"5","departname":"党政办","head":"李五","address":""},{"departid":"6","departname":"计生办","head":"李六","address":""},{"departid":"7","departname":"民政局","head":"李七","address":""}]
     */

    private int errno;
    private String error;
    private List<DataBean> data;

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * departid : 1
         * departname : 果布戛
         * head : 李一
         * address :
         */

        private String departid;
        private String departname;
        private String head;
        private String address;

        public String getDepartid() {
            return departid;
        }

        public void setDepartid(String departid) {
            this.departid = departid;
        }

        public String getDepartname() {
            return departname;
        }

        public void setDepartname(String departname) {
            this.departname = departname;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}