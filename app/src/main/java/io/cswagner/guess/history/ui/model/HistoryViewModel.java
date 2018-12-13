package io.cswagner.guess.history.ui.model;

public abstract class HistoryViewModel {

    private HistoryViewModel() {}

    public static final class Default extends HistoryViewModel {

        public static final Default INSTANCE = new Default();

        private Default() {}
    }

    public static final class Loading extends HistoryViewModel {

        public static final Loading INSTANCE = new Loading();

        private Loading() {}
    }

    public static final class Loaded extends HistoryViewModel {

        public final int count;

        public Loaded(int count) {
            this.count = count;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            } else if (obj instanceof Loaded) {
                Loaded that = (Loaded)obj;
                return this.count == that.count;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return count;
        }
    }

    public static final class Error extends HistoryViewModel {

        public static final Error INSTANCE = new Error();

        private Error() {}
    }
}
