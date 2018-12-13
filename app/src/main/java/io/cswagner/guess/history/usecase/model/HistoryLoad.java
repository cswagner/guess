package io.cswagner.guess.history.usecase.model;

public abstract class HistoryLoad {

    private HistoryLoad() {}

    public static final class InProgress extends HistoryLoad {

        public static final InProgress INSTANCE = new InProgress();

        private InProgress() {}
    }

    public static final class Success extends HistoryLoad {

        public final int count;

        public Success(int count) {
            this.count = count;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            } else if (obj instanceof Success) {
                Success that = (Success)obj;
                return this.count == that.count;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return count;
        }
    }

    public static final class Failure extends HistoryLoad {

        public static final Failure INSTANCE = new Failure();

        private Failure() {}
    }

    public static final class Cancelled extends HistoryLoad {

        public static final Cancelled INSTANCE = new Cancelled();

        private Cancelled() {}
    }
}
