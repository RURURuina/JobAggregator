package ru.practicum.android.diploma.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/*предлогаю пользовать эту функцию, она не блокирует поток
Функция debounce используется для отложенного выполнения действия.
 Она принимает три параметра: время задержки в миллисекундах, объект
 CoroutineScope и логический параметр useLastParam. Если useLastParam
  установлен в true, то предыдущая отложенная задача будет отменена перед
   запуском новой. Внутри функции создается объект Job, который отвечает
   за отложенное выполнение действия. Если useLastParam установлен в true
    или предыдущая отложенная задача уже завершена, то новая отложенная задача
    запускается с помощью функции launch. Внутри отложенной задачи используется
    функция delay для задержки выполнения на указанное время, а затем выполняется
    действие с помощью переданной функции action. */
fun <T> debounce(
    delayMillis: Long,
    coroutineScope: CoroutineScope,
    useLastParam: Boolean,
    action: (T) -> Unit,
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        if (useLastParam) {
            debounceJob?.cancel()
        }
        if (debounceJob?.isCompleted != false || useLastParam) {
            debounceJob = coroutineScope.launch {
                delay(delayMillis)
                action(param)
            }
        }
    }
}

/*тот же дебоунс, что и ранее созданный,
 но проще использовать и настраивать в
 том месте, в котором он используется*/
