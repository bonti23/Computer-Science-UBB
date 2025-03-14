package sem1_2.decorator;

public class DelayTaskRunner extends AbstractTaskRunner {

    public DelayTaskRunner(TaskRunner taskRunner) {
        super(taskRunner);
    }

    @Override
    public void executeAll(){
        while(hasTask()){
            executeOneTask();
            try{
                Thread.sleep(3000);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
