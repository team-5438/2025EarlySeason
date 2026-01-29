package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;

public class AngleMeasurement extends Command {
    private SwerveSubsystem swerveSubsystem;
    public AngleMeasurement(SwerveSubsystem swerveSubsystem) {
        this.swerveSubsystem = swerveSubsystem;
    
        addRequirements(swerveSubsystem);
    }
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        swerveSubsystem.getPose();
    }
}
