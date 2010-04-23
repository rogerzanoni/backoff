entityManager = luajava.bindClass( "com.deadtroll.backoff.engine.manager.EntityManager" )
mapManager = luajava.bindClass( "com.deadtroll.backoff.engine.manager.MapManager" )
angle = luajava.bindClass( "com.deadtroll.backoff.engine.helper.Angle" )

function act(zombie)
	player = entityManager:getInstance():getPlayer()

	if (not zombie:isCollided()) then
		zombie:setPreviousRotation(zombie:getRotation())
		zombie:setPreviousPosition(zombie:getPosition())

		zombie:setDestination(player:getCenter())
		viewPort = mapManager:getInstance():getViewPort()
		destination = zombie:getDestination()

		vectToDestination = destination:copy():sub(zombie:getPosition())
		heading = vectToDestination:getNormal()

		heading:scale(zombie:getSpeed())
		newPosition = zombie:getPosition():add(heading)

		zombie:setPosition(newPosition)
		zombie:setRotation(angle:calcAngle2D(zombie:getPosition(), player:getCenter()))
	else
		zombie:setCollided(false)
		zombie:setPosition(zombie:getPreviousPosition())
		zombie:setRotation(angle:calcAngle2D(zombie:getPosition(), player:getCenter()))
		--TODO: modificar logica, criar estado para wallhit
	end
end
