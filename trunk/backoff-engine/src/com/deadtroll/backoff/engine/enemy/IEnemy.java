package com.deadtroll.backoff.engine.enemy;

import com.deadtroll.backoff.engine.model.IDamageable;
import com.deadtroll.backoff.engine.model.IGameObject;
import com.deadtroll.backoff.engine.model.IMoveable;
import com.deadtroll.backoff.engine.model.IScoreable;
import com.deadtroll.backoff.engine.model.ITransient;
import com.deadtroll.backoff.engine.sound.ISoundSource;

public interface IEnemy extends IEnemyAcessor, IGameObject, IMoveable, ITransient, IDamageable, IScoreable, ISoundSource {
}
