function create(zombie)
	zombie:setEnergy(16)
	rotation = (zombie:getRotation()/180.0)*math.pi
	zombie:setRotation(rotation)		
	zombie:setSpriteSheet("res/sprites/zombie_topdown.png", 29, 18)
	zombie:setSpeed(0.8)
	zombie:setDamage(2)
	zombie:setScore(100)
end