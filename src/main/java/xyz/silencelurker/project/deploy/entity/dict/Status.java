package xyz.silencelurker.project.deploy.entity.dict;

/**
 * @author Silence_Lurker
 */
public enum Status {
    /**
     * 创建
     */
    CREATE,
    /**
     * 启动
     */
    START,
    /**
     * 等待取件
     */
    WAITING_FOR_PICKUP,
    /**
     * 已取件
     */
    PICKED_UP,
    /**
     * 处理中
     */
    PROCESSING,
    /**
     * 已完成
     */
    COMPLETED,
    /**
     * 取消
     */
    CANCEL,
    /**
     * 失败
     */
    FAILED;

    Status() {
    }
}
