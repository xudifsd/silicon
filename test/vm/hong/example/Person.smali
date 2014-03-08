.class public Lhong/example/Person;
.super Ljava/lang/Object;
.source "Person.java"


# instance fields
.field private age:I

.field private name:Ljava/lang/String;


# direct methods
.method public constructor <init>()V
    .registers 1

    .prologue
    .line 25
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 27
    return-void
.end method

.method public constructor <init>(Ljava/lang/String;I)V
    .registers 3
    .parameter
    .parameter

    .prologue
    .line 21
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 22
    iput-object p1, p0, Lhong/example/Person;->name:Ljava/lang/String;

    .line 23
    iput p2, p0, Lhong/example/Person;->age:I

    .line 24
    return-void
.end method

.method public static main([Ljava/lang/String;)V
    .registers 8
    .parameter

    .prologue
    .line 29
    new-instance v0, Lhong/example/Person;

    const-string v1, "xiaoming"

    const/16 v2, -0xb

    invoke-direct {v0, v1, v2}, Lhong/example/Person;-><init>(Ljava/lang/String;I)V

    .line 30
    new-instance v1, Lhong/example/Person;

    invoke-direct {v1}, Lhong/example/Person;-><init>()V

    .line 31
    new-instance v2, Ljava/util/HashMap;

    invoke-direct {v2}, Ljava/util/HashMap;-><init>()V

    .line 32
    new-instance v3, Lhong/example/Process;

    invoke-direct {v3}, Lhong/example/Process;-><init>()V

    .line 33
    sget-object v4, Ljava/lang/System;->out:Ljava/io/PrintStream;

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v0}, Lhong/example/Person;->getName()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, " "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v0}, Lhong/example/Person;->getAge()I

    move-result v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    .line 34
    const/16 v4, 0x16

    invoke-virtual {v0, v4}, Lhong/example/Person;->setAge(I)V

    .line 35
    sget-object v4, Ljava/lang/System;->out:Ljava/io/PrintStream;

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v0}, Lhong/example/Person;->getName()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, " "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v0}, Lhong/example/Person;->getAge()I

    move-result v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    .line 36
    const-string v4, "haojian"

    invoke-virtual {v1, v4}, Lhong/example/Person;->setName(Ljava/lang/String;)V

    .line 37
    const/16 v4, 0x11

    invoke-virtual {v1, v4}, Lhong/example/Person;->setAge(I)V

    .line 38
    invoke-virtual {v1}, Lhong/example/Person;->getName()Ljava/lang/String;

    move-result-object v4

    new-instance v5, Ljava/lang/Integer;

    invoke-virtual {v1}, Lhong/example/Person;->getAge()I

    move-result v6

    invoke-direct {v5, v6}, Ljava/lang/Integer;-><init>(I)V

    invoke-interface {v2, v4, v5}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 39
    sget-object v2, Ljava/lang/System;->out:Ljava/io/PrintStream;

    invoke-virtual {v0}, Lhong/example/Person;->getAge()I

    move-result v4

    invoke-virtual {v1}, Lhong/example/Person;->getAge()I

    move-result v5

    invoke-virtual {v3, v4, v5}, Lhong/example/Process;->add(II)I

    move-result v3

    invoke-virtual {v2, v3}, Ljava/io/PrintStream;->println(I)V

    .line 40
    sget-object v2, Ljava/lang/System;->out:Ljava/io/PrintStream;

    invoke-virtual {v0}, Lhong/example/Person;->getName()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v1}, Lhong/example/Person;->getName()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lhong/example/Process;->combine(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v2, v0}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    .line 41
    return-void
.end method


# virtual methods
.method public getAge()I
    .registers 2

    .prologue
    .line 16
    iget v0, p0, Lhong/example/Person;->age:I

    return v0
.end method

.method public getName()Ljava/lang/String;
    .registers 2

    .prologue
    .line 19
    iget-object v0, p0, Lhong/example/Person;->name:Ljava/lang/String;

    return-object v0
.end method

.method public setAge(I)V
    .registers 2
    .parameter

    .prologue
    .line 10
    iput p1, p0, Lhong/example/Person;->age:I

    .line 11
    return-void
.end method

.method public setName(Ljava/lang/String;)V
    .registers 2
    .parameter

    .prologue
    .line 13
    iput-object p1, p0, Lhong/example/Person;->name:Ljava/lang/String;

    .line 14
    return-void
.end method
