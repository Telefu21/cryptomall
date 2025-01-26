package io.ozgard.cryptomall.event;

import org.springframework.context.ApplicationEvent;

import javafx.stage.Stage;

public class StageReadyEvent extends ApplicationEvent 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8059108440213319495L;
	private final Stage stage;
	

    public Stage getStage() 
    {
		return stage;
	}

	public StageReadyEvent(Stage stage) 
    {
        super(stage);
        this.stage = stage;
    }
}
