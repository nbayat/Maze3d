package Controller;

public class CameraControl {// idée de base basée sur le projet de pong probléme si on veut pouvoir avaancer
                            // en diagoanal
    public enum StateCam {
        LEFT,
        RIGHT,
        IDLE

    }

    StateCam state = StateCam.IDLE;

    public void setState(StateCam state) {
        this.state = state;
    }

    public StateCam geState() {
        return state;
    }

}