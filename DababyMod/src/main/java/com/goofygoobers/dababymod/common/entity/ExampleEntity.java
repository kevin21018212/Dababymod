package com.goofygoobers.dababymod.common.entity;

import com.goofygoobers.dababymod.core.entity;
import com.goofygoobers.dababymod.core.genericItem;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
public class ExampleEntity extends Animal {
	
  public ExampleEntity(EntityType<? extends Animal> entityType, Level level){
	  super(entityType, level);
  }

@Override
	protected void registerGoals() {
		super.registerGoals();
		  this.goalSelector.addGoal(0, new FloatGoal(this));
	      this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
	      this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
	      this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(genericItem.DABlOON.get()), false));
	      this.goalSelector.addGoal(1, new FloatGoal(this));
	    
	      this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
	      this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));

	      this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
	      this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));

	      this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
	      this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
	     
	      this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
	    
	
	}
public static AttributeSupplier.Builder createAttributes(){
	return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, .24D);
}
@Override
public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob parent) {
	// TODO Auto-generated method stub
	return entity.EXAMPLE_ENTITY.get().create(level);
}
}
