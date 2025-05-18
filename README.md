# Szkript nyelv AST generálással

- Operátorok, műveletek: +, -, *, /, (, ), abs(), min(), max(), =, ==, !=, <, >
- Ternary operátor a következő formában: (cond) ? expr : expr
- print(...) utasítás, ami a paraméterül kapott kifejezést, vagy kifejezéseket kiírja.
- scan(...) utasítás, ami egy számot olvas be és a paraméterül adott változók ezt kapják értékül.
- A kifejezés levelében lehet szám, lehet változónév és lehet TIME is, ami az aktuális idő (a UNIX idő, azaz az 1970 óta eltelt másodpercek száma).
- Lehet benne változókat deklarálni double típusokkal. Nem deklarált változók használata vagy változó újradeklarálása esetén hiba keletkezik. Ha a változónak deklaráláskor nem adunk értéket, akkor alapértelmezetten 0-át kap.
- A del utasítással lehet változót törölni.
- Tartalmaz for és while ciklust C szerű szintaktikával.
- Van benne if és switch-case-default utasítás.
- A # szimbólummal lehetőség van komment beszúrására.
- Továbbá tartalmazza az órán megvalósított függvény definiálást, hívást és a hatványozást.

### Példa inputok:
```
### exmple 1 ###
double starttime
double sum
starttime = TIME
sum = 0
for (double x = 0; x < 100000; x = x + 1) {
  sum = sum + x
}
print(sum)
print(TIME - starttime)
```

```
### example 2 ###
# Ez az input fuggvenyeben futhat helyesen
# vagy okozhat futasi hibat
double x
scan(x)
while(x>0) {
    switch(x) {
        case 1:
            double y
            scan(y)
            break
        case 2:
            del y
        case 3:
            x = 5
            break
        default:
            x = 10
    }
    scan(x)
}
print(x+y)
```
```
### example 3 ###
double a
double b
scan(b)

if (b >= a) {
    print(a, b)
} else {
    print(abs(b))
}

double c
scan(b, c)
if (c != b) {
    print(1)
}

scan(a)
switch (a) {
    case 1:
      print(1)
      break

    case 5-3:
      print(2)

    default:
      print(42)

    case 3:
      print(3)
      break
}

function triple_pow(a, b) returns 3*a^b

scan(c)
b = (c < 0) ? min(-10, c) : triple_pow(c, 3)
print(b)
```
