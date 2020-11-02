Основные изменения, внесенные в исходный код:

1. Мы не сканируем факт появления новых сообщений, а один раз фиксируем его при запуске и далее считываем последние 300 000 сообщений.

2. Соотношение потоков на чтение новостей и потоков на анализ распределим как 2:30, т.к. deep-ai отвечает гораздо медленнее, чем мы скачиваем новости.

3. Поскольку основные задержки связаны не локальными ресурсами, а с производительностью deep-ai, то увеличиваем количество потоков на 1 ядро CPU (в случае процессора с HT количество "ядер" удваивается) до 32.

4. Ценовая политика deep-ai связана с количеством запросов (но не с их размерами), поэтому мы посылаем сообщение целиком, а на предложения делим уже после получения ответа. Также это снижает накладные расходы по созданию соединений.

5. Часто вместо java упоминают javascript, поэтому мы еще созданим список стоп-слов, при наличии которых мы не будем учитывать предложение.

6. Если во время запроса у нас происходит ошибка, то мы возвращаем этот текст обратно в очередь и пытаемся его повторно проанализировать еще 49 раз (всего 50 попыток).

7. Окончанием обработки для нас будет совместное условие: скачены все сообщения и буфер для обработки пуст.

`
Current last id: 24961816, current datetime: 02.11.2020 00:28:06
[pool-1-thread-11] stats: {python=Stats{mentions=1, score=-1, rating=-0,50}, java=Stats{mentions=0, score=0, rating=0,00}}, queue size: 0
[pool-1-thread-1] id: 24662000, queue size: 0, mentions: 1, current datetime: 02.11.2020 00:28:10

...

[pool-1-thread-25] stats: {python=Stats{mentions=31, score=-20, rating=-0,63}, java=Stats{mentions=3, score=-1, rating=-0,25}}, queue size: 209
[pool-1-thread-5] id: 24687000, queue size: 214, mentions: 34, current datetime: 02.11.2020 00:38:33

...

[pool-1-thread-40] stats: {python=Stats{mentions=129, score=-105, rating=-0,81}, java=Stats{mentions=80, score=-63, rating=-0,78}}, queue size: 1920
[pool-1-thread-4] id: 24813000, queue size: 1922, mentions: 209, current datetime: 02.11.2020 01:30:30

...

[pool-1-thread-128] stats: {python=Stats{mentions=269, score=-197, rating=-0,73}, java=Stats{mentions=154, score=-118, rating=-0,76}}, queue size: 4443
[pool-1-thread-7] id: 24961000, queue size: 4441, mentions: 423, current datetime: 02.11.2020 02:31:03
[pool-1-thread-3] close download thread, current datetime: 02.11.2020 02:31:23
[pool-1-thread-7] close download thread, current datetime: 02.11.2020 02:31:23
[pool-1-thread-6] close download thread, current datetime: 02.11.2020 02:31:23
[pool-1-thread-2] close download thread, current datetime: 02.11.2020 02:31:23
[pool-1-thread-8] close download thread, current datetime: 02.11.2020 02:31:23
[pool-1-thread-5] close download thread, current datetime: 02.11.2020 02:31:23
[pool-1-thread-4] close download thread, current datetime: 02.11.2020 02:31:23
[pool-1-thread-1] close download thread, current datetime: 02.11.2020 02:31:23

Final stats: {python=Stats{mentions=1147, score=-847, rating=-0,74}, java=Stats{mentions=768, score=-583, rating=-0,76}}
Total mentors: 1915
 `

Вывод: упоминание python происходило чаще, к программированию, у участников данного ресурса, слабоотрицательное отношение.
