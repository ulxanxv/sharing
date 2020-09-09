# Sharing

## Инструкция по получению информации о дисках

+ [{url: 'user/getMyDisks', method: 'GET'}] — список дисков пользователя
+ [{url: 'user/getFreeDisks', method: 'GET'}] — список свободных дисков (которые можно взять)
+ [{url: 'user/getTakenDisks', method: 'GET'}] — список дисков, взятых пользователем
+ [{url: 'user/getTakenFromUser', method: 'GET'}] — список дисков, взятых у пользователя (с указанием, кто взял)

## Инструкция по обменным операциям

+ [{url: '/user/getDisk/{id}', method: 'GET'}] — взять диск взаймы
+ [{url: '/user/returnDisk/{id}}', method: 'GET'}] — вернуть диск, который был взят ранее
