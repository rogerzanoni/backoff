mapManager = luajava.bindClass( "com.deadtroll.backoff.engine.manager.MapManager" )
angle = luajava.bindClass( "com.deadtroll.backoff.engine.helper.Angle" )
gameScene = luajava.bindClass( "com.deadtroll.backoff.scene.GameScene")

function random_destination(zombie) 
	if (zombie:getDestination() == nil or math.random() < 0.01) then
		randomX = math.random()*gameScene.WORLD_WIDTH
		randomY = math.random()*gameScene.WORLD_HEIGHT
		newDestination = luajava.newInstance("org.newdawn.slick.geom.Vector2f", randomX, randomY)
		return newDestination
	end
	return zombie:getDestination()
end

function act(zombie)	
    destinationDistance = zombie:getCenter():distance(zombie:getDestination())
	if (destinationDistance > 20.0) then
		if (not zombie:isCollided()) then
			zombie:setPreviousRotation(zombie:getRotation())
			zombie:setPreviousPosition(zombie:getPosition())
			viewPort = mapManager:getInstance():getViewPort()
			destination = zombie:getDestination()
		
			vectToDestination = destination:copy():sub(zombie:getPosition())
			heading = vectToDestination:getNormal()
			
			heading:scale(zombie:getSpeed())
			newPosition = zombie:getPosition():add(heading)
			
			zombie:setPosition(newPosition)
			zombie:setRotation(angle:calcAngle2D(zombie:getPosition(), destination))
		else
			zombie:setCollided(false)
			zombie:setRotation(zombie:getPreviousRotation())
			zombie:setPosition(zombie:getPreviousPosition())
		end
	else
		zombie:setDestination(random_destination(zombie))
	end
end
