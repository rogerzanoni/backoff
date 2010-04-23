function create(zombie)
	zombie:setEnergy(10)
	rotation = (zombie:getRotation()/180.0)*math.pi		
	zombie:setRotation(rotation)
	zombie:setSpriteSheet("res/sprites/fastzombie_topdown.png", 29, 18)
	zombie:setSpeed(1.5)
	zombie:setDamage(1)
	zombie:setScore(150)
end