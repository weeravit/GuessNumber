package me.weeravit.guessnumber.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RandomResponse {

    @SerializedName("jsonrpc")
    @Expose
    private String jsonrpc;
    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("id")
    @Expose
    private Integer id;

    public boolean isCorrect(int guessNumber) {
        if (result.getRandom().getData().get(0) == guessNumber) {
            return true;
        }
        return false;
    }

    public int getResultNumber() {
        return result.getRandom().getData().get(0);
    }

    public String getMessageResult(int guessNumber) {
        if (guessNumber > getResultNumber()) {
            return "Your guess is " + guessNumber + " greater than result!";
        } else if (guessNumber < getResultNumber()) {
            return "Your guess is " + guessNumber + " less than result!";
        } else {
            return "Winner!";
        }
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static class Result {

        @SerializedName("random")
        @Expose
        private Random random;
        @SerializedName("bitsUsed")
        @Expose
        private Integer bitsUsed;
        @SerializedName("bitsLeft")
        @Expose
        private Integer bitsLeft;
        @SerializedName("requestsLeft")
        @Expose
        private Integer requestsLeft;
        @SerializedName("advisoryDelay")
        @Expose
        private Integer advisoryDelay;

        public Random getRandom() {
            return random;
        }

        public void setRandom(Random random) {
            this.random = random;
        }

        public Integer getBitsUsed() {
            return bitsUsed;
        }

        public void setBitsUsed(Integer bitsUsed) {
            this.bitsUsed = bitsUsed;
        }

        public Integer getBitsLeft() {
            return bitsLeft;
        }

        public void setBitsLeft(Integer bitsLeft) {
            this.bitsLeft = bitsLeft;
        }

        public Integer getRequestsLeft() {
            return requestsLeft;
        }

        public void setRequestsLeft(Integer requestsLeft) {
            this.requestsLeft = requestsLeft;
        }

        public Integer getAdvisoryDelay() {
            return advisoryDelay;
        }

        public void setAdvisoryDelay(Integer advisoryDelay) {
            this.advisoryDelay = advisoryDelay;
        }

        public static class Random {

            @SerializedName("data")
            @Expose
            private List<Integer> data = new ArrayList<>();
            @SerializedName("completionTime")
            @Expose
            private String completionTime;

            public List<Integer> getData() {
                return data;
            }

            public void setData(List<Integer> data) {
                this.data = data;
            }

            public String getCompletionTime() {
                return completionTime;
            }

            public void setCompletionTime(String completionTime) {
                this.completionTime = completionTime;
            }

        }
    }
}