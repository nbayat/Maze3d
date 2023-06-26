package Controller;

public class PlayerControl {// idée de base basée sur le projet de pong probléme si on veut pouvoir avaancer
                            // en diagoanal
    public enum State {
        FORWARD,
        BACKWARD,
        RIGHT,
        LEFT,
        IDLE

    }

    State state = State.IDLE;

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

}
