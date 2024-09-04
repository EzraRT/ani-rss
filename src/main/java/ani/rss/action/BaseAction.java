package ani.rss.action;

import ani.rss.entity.Result;
import ani.rss.util.ServerUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.http.server.HttpServerResponse;
import cn.hutool.http.server.action.Action;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public interface BaseAction extends Action {
    Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .create();

    default <T> T getBody(Class<T> tClass) {
        return gson.fromJson(ServerUtil.request.get().getBody(), tClass);
    }

    default <T> void resultSuccess() {
        result(Result.success());
    }

    default <T> void resultSuccess(T t) {
        result(Result.success(t));
    }

    default <T> void resultSuccessMsg(String t) {
        result(Result.success().setMessage(t));
    }

    default <T> void resultError() {
        result(Result.error());
    }

    default <T> void resultError(T t) {
        result(Result.error(t));
    }

    default <T> void resultErrorMsg(String t) {
        result(Result.error().setMessage(t));
    }

    default <T> void result(Result<T> result) {
        HttpServerResponse httpServerResponse = ServerUtil.response.get();
        httpServerResponse.setContentType("application/json; charset=utf-8");
        String json = gson.toJson(result);
        IoUtil.writeUtf8(httpServerResponse.getOut(), true, json);
    }
}