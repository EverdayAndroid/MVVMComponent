package com.everday.lib_base.command;
/**
* @author Everday
* @emil wangtaohandsome@gmail.com
* create at 2019/3/22
* description: 执行的命令回调，用于VidewModel与xml之间的数据绑定
*/

public class BindingCommand<T> {
    private BindingAction execute;
    private BindingConsumer<T> consumer;
    private BindingFunction<Boolean> canExecute0;

    public BindingCommand(BindingAction execute){
        this.execute = execute;
    }

    public BindingCommand(BindingConsumer<T> execute) {
        this.consumer = execute;
    }

    /**
     *
     * @param execute  触发命令
     * @param canExecute   true则执行，反之不执行
     */
    public BindingCommand(BindingAction execute,BindingFunction<Boolean> canExecute){
        this.execute = execute;
        this.canExecute0 = canExecute;
    }

    /**
     *
     * @param execute      带泛型参数出发命令
     * @param canExecute   true则执行，反之不执行
     */
    public BindingCommand(BindingConsumer<T> execute,BindingFunction<Boolean> canExecute){
        this.consumer = execute;
        this.canExecute0 = canExecute;
    }


    public void execute(){
        if(execute != null && canExecute()){
            execute.call();
        }
    }

    public void execute(T parameter){
        if(consumer!=null && canExecute()){
            consumer.call(parameter);
        }
    }


    private boolean canExecute(){
        if(canExecute0 == null){
            return true;
        }
        return canExecute0.call();
    }
}
