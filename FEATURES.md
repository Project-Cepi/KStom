# Features

## Kommand

Kommand is a wrapper around Command (yes, K quirky). It allows for
easy command building with the kotlin DSL

```kotlin
object New : Kommand({
    val add by literal // shorthand for val add = ArgumentType.Literal("add")
    val remove by literal
    val set by literal

    val amount = ArgumentType.Integer("amount").min(0)

    syntax {
        sender.sendMessage("Usage: add|remove|set <amount>")
    }

    subcommand("sub") {
        onlyPlayers()

        val delete by literal

        syntax(delete, amount) {
            player.level += !amount
        }
    }

    syntax(add, amount) {
        player.level += !amount
    }.onlyPlayers()

    syntax(remove, amount) {
        player.level = (player.level - !amount).coerceAtLeast(0)
    }.onlyPlayers()

    syntax(set, amount) {
        player.level = !amount
    }.condition { player?.level == 5 } // not realistic but demonstrates custom conditions


}, "hey")
```

You can register commands by using `CommandObject.register()` somewhere
in your extension's initialize function. (`#unregister` works as well.)

## Events

Use reified generics to make listening to events a much more pleasent experience.

```kotlin
Manager.globalEvent.listenOnly<EntityTickEvent> {
    entity.removeEffect(PotionEffect.ABSORPTION)
}

Manager.extension.getExtension(extensionName)?.node?.listenOnly<EntityTickEvent> {
    entity.removeEffect(PotionEffect.ABSORPTION)
}

Manager.globalEvent.listen<EntityTickEvent> {
    expireCount = 50
    removeWhen { entity.isCustomNameVisible }
    filters += { entity.isGlowing }
    handler {
        entity.setGravity(5.0, .5)
    }
}
```

## Manager

Use an object to easily access MinecraftServer managers.

```kotlin
MinecraftServer.getSchedulerManager() // old
Manager.scheduler // new
```

## Position spreading

Break down Points into seperate coordinates.

```kotlin
fun old(position: Pos) {
    val x = position.x()
    val y = position.y()
    val z = position.z()
    val yaw = position.yaw
    val pitch = position.pitch
}

fun new(position: Pos) {
    val (x, y, z, yaw, pitch) = position
}
```