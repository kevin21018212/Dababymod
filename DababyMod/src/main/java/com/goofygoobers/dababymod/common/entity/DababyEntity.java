package com.goofygoobers.dababymod.common.entity;

import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.goofygoobers.dababymod.dababymod;
import com.goofygoobers.dababymod.core.genericItem;
import com.goofygoobers.dababymod.sounds.DababySounds;
import com.google.common.collect.Sets;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;


public class DababyEntity extends TamableAnimal implements NeutralMob {
	   private static final EntityDataAccessor<Boolean> DATA_INTERESTED_ID = SynchedEntityData.defineId(DababyEntity.class, EntityDataSerializers.BOOLEAN);
	    private static final ResourceLocation LOOT_TABLE = new ResourceLocation(dababymod.MODID,"entities/dababy_entity");
	   private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(DababyEntity.class, EntityDataSerializers.INT);
	   public static final Predicate<LivingEntity> PREY_SELECTOR = (p_30437_) -> {
	      EntityType<?> entitytype = p_30437_.getType();
	      return entitytype == EntityType.SHEEP || entitytype == EntityType.PIG || entitytype == EntityType.SPIDER ||  entitytype == EntityType.ZOMBIE || entitytype == EntityType.SKELETON;
	   };
	 
	   private static final Item POISONOUS_FOOD = Items.IRON_INGOT;
	   private static final Set<Item> TAME_FOOD =  Sets.newHashSet(Items.GOLD_INGOT,Items.DIAMOND,Items.EMERALD);
	   private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	   @Nullable
	   private UUID persistentAngerTarget;

	   public DababyEntity(EntityType<? extends DababyEntity> p_30369_, Level p_30370_) {
	      super(p_30369_, p_30370_);
	      this.setTame(false);
	   }

	   protected void registerGoals() {
	      this.goalSelector.addGoal(1, new FloatGoal(this));
	      this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));

	      this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
	      this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
	      this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
	      this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
	      this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
	      this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(genericItem.DABlOON.get()), false));
	      this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
	      this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
	      this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
	      this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
	      this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
	      this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
	      this.targetSelector.addGoal(5, new NonTameRandomTargetGoal<>(this, Animal.class, false, PREY_SELECTOR));
	      this.targetSelector.addGoal(6, new NonTameRandomTargetGoal<>(this, Turtle.class, false, Turtle.BABY_ON_LAND_SELECTOR));
	      this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, AbstractSkeleton.class, false));
	      this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal<>(this, true));
	   }

	   public static AttributeSupplier.Builder createAttributes() {
	      return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, (double)0.6F).add(Attributes.MAX_HEALTH, 16.0D).add(Attributes.ATTACK_DAMAGE, 14D);
	   }

	   protected void defineSynchedData() {
	      super.defineSynchedData();
	      this.entityData.define(DATA_INTERESTED_ID, false);
	   
	      this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
	   }

	   protected void playStepSound(BlockPos p_30415_, BlockState p_30416_) {
	      this.playSound(DababySounds.letsgo, 0.15F, 1.0F);
	   }


	   protected SoundEvent getAmbientSound() {
	      if (this.isAngry()) {
	         return DababySounds.ipullup;
	      } else if (this.random.nextInt(3) == 0) {
	         return this.isTame() && this.getHealth() < 10.0F ? DababySounds.nocap: DababySounds.letsgo;
	      } else {
	         return DababySounds.yeah;
	      }
	   }

	   protected SoundEvent getHurtSound(DamageSource p_30424_) {
		return DababySounds.ha;
	   }

	   protected SoundEvent getDeathSound() {
	      return  DababySounds.ha;
	   }

	   protected float getSoundVolume() {
	      return 0.4F;
	   }

	  

	

	   public void die(DamageSource p_30384_) {

	      super.die(p_30384_);
	   }

	 
	  
	
	   protected void handleAttributes(float p_34340_) {
		
		      this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).addPermanentModifier(new AttributeModifier("Random spawn bonus", this.random.nextDouble() * (double)0.05F, AttributeModifier.Operation.ADDITION));
		      double d0 = this.random.nextDouble() * 1.5D * (double)p_34340_;
		      if (d0 > 1.0D) {
		         this.getAttribute(Attributes.FOLLOW_RANGE).addPermanentModifier(new AttributeModifier("Random zombie-spawn bonus", d0, AttributeModifier.Operation.MULTIPLY_TOTAL));
		      }

		      if (this.random.nextFloat() < p_34340_ * 0.05F) {
		         this.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).addPermanentModifier(new AttributeModifier("Leader bonus", this.random.nextDouble() * 0.25D + 0.5D, AttributeModifier.Operation.ADDITION));
		         this.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier("Leader  bonus", this.random.nextDouble() * 3.0D + 1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL));
		       
		      }

		   }

	

	   public boolean hurt(DamageSource p_30386_, float p_30387_) {
	      if (this.isInvulnerableTo(p_30386_)) {
	         return false;
	      } else {
	         Entity entity = p_30386_.getEntity();
	         this.setOrderedToSit(false);
	         if (entity != null && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
	            p_30387_ = (p_30387_ + 1.0F) / 2.0F;
	         }

	         return super.hurt(p_30386_, p_30387_);
	      }
	   }

	   public boolean doHurtTarget(Entity p_30372_) {
	      boolean flag = p_30372_.hurt(DamageSource.mobAttack(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
	      if (flag) {
	         this.doEnchantDamageEffects(this, p_30372_);
	      }

	      return flag;
	   }

	   public void setTame(boolean p_30443_) {
	      super.setTame(p_30443_);
	      if (p_30443_) {
	         this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(40.0D);
	         this.setHealth(40.0F);
	      } else {
	         this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(16.0D);
	      }

	      this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(10.0D);
	   }

	 
	   public InteractionResult mobInteract(Player p_29414_, InteractionHand p_29415_) {
		      ItemStack itemstack = p_29414_.getItemInHand(p_29415_);
		      if (!this.isTame() && TAME_FOOD.contains(itemstack.getItem())) {
		         if (!p_29414_.getAbilities().instabuild) {
		            itemstack.shrink(1);
		         }

		         if (!this.isSilent()) {
		            this.level.playSound((Player)null, this.getX(), this.getY(), this.getZ(), DababySounds.letsgo, this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
		         }

		         if (!this.level.isClientSide) {
		            if (this.random.nextInt(10) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, p_29414_)) {
		               this.tame(p_29414_);
		               this.level.broadcastEntityEvent(this, (byte)7);
		            } else {
		               this.level.broadcastEntityEvent(this, (byte)6);
		            }
		         }

		         return InteractionResult.sidedSuccess(this.level.isClientSide);
		      } else if (itemstack.is(POISONOUS_FOOD)) {
		         if (!p_29414_.getAbilities().instabuild) {
		            itemstack.shrink(1);
		         }

		         this.addEffect(new MobEffectInstance(MobEffects.POISON, 6));
		         if (p_29414_.isCreative() || !this.isInvulnerable()) {
		            this.hurt(DamageSource.playerAttack(p_29414_), Float.MAX_VALUE);
		         }

		         return InteractionResult.sidedSuccess(this.level.isClientSide);
		      } else if (this.isTame() && this.isOwnedBy(p_29414_)) {
		         if (!this.level.isClientSide) {
		            this.setOrderedToSit(!this.isOrderedToSit());
		         }

		         return InteractionResult.sidedSuccess(this.level.isClientSide);
		      } else {
		         return super.mobInteract(p_29414_, p_29415_);
		      }
		   }

	 

	   public boolean isFood(ItemStack p_30440_) {
	      Item item = p_30440_.getItem();
	      return item.isEdible() && item.getFoodProperties().isMeat();
	   }

	   public int getMaxSpawnClusterSize() {
	      return 8;
	   }

	   public int getRemainingPersistentAngerTime() {
	      return this.entityData.get(DATA_REMAINING_ANGER_TIME);
	   }

	   public void setRemainingPersistentAngerTime(int p_30404_) {
	      this.entityData.set(DATA_REMAINING_ANGER_TIME, p_30404_);
	   }

	   public void startPersistentAngerTimer() {
	      this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
	   }

	   @Nullable
	   public UUID getPersistentAngerTarget() {
	      return this.persistentAngerTarget;
	   }

	   public void setPersistentAngerTarget(@Nullable UUID p_30400_) {
	      this.persistentAngerTarget = p_30400_;
	   }

	

	  


	   public boolean isInterested() {
	      return this.entityData.get(DATA_INTERESTED_ID);
	   }

	   public boolean wantsToAttack(LivingEntity p_30389_, LivingEntity p_30390_) {
	      if (!(p_30389_ instanceof Creeper) && !(p_30389_ instanceof Ghast)) {
	         if (p_30389_ instanceof DababyEntity) {
	            DababyEntity dababy = (DababyEntity)p_30389_;
	            return !dababy.isTame() || dababy.getOwner() != p_30390_;
	         } else if (p_30389_ instanceof Player && p_30390_ instanceof Player && !((Player)p_30390_).canHarmPlayer((Player)p_30389_)) {
	            return false;
	         } else if (p_30389_ instanceof AbstractHorse && ((AbstractHorse)p_30389_).isTamed()) {
	            return false;
	         } else {
	            return !(p_30389_ instanceof TamableAnimal) || !((TamableAnimal)p_30389_).isTame();
	         }
	      } else {
	         return false;
	      }
	   }

	   public boolean canBeLeashed(Player p_30396_) {
	      return !this.isAngry() && super.canBeLeashed(p_30396_);
	   }

	   public Vec3 getLeashOffset() {
	      return new Vec3(0.0D, (double)(0.6F * this.getEyeHeight()), (double)(this.getBbWidth() * 0.4F));
	   }

	   public static boolean checkDababyEntitySpawnRules(EntityType<DababyEntity> p_186244_, LevelAccessor p_186245_, MobSpawnType p_186246_, BlockPos p_186247_, Random p_186248_) {
	      return p_186245_.getBlockState(p_186247_.below()).is(BlockTags.WOLVES_SPAWNABLE_ON) && isBrightEnoughToSpawn(p_186245_, p_186247_);
	   }

	   class DababyEntityAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
	      private final DababyEntity dababy;

	      public DababyEntityAvoidEntityGoal(DababyEntity p_30454_, Class<T> p_30455_, float p_30456_, double p_30457_, double p_30458_) {
	         super(p_30454_, p_30455_, p_30456_, p_30457_, p_30458_);
	         this.dababy = p_30454_;
	      }

	      public boolean canUse() {
	         if (super.canUse() && this.toAvoid instanceof Llama) {
	            return !this.dababy.isTame() && this.avoidLlama((Llama)this.toAvoid);
	         } else {
	            return false;
	         }
	      }

	      private boolean avoidLlama(Llama p_30461_) {
	         return p_30461_.getStrength() >= DababyEntity.this.random.nextInt(5);
	      }

	      public void start() {
	         DababyEntity.this.setTarget((LivingEntity)null);
	         super.start();
	      }

	      public void tick() {
	         DababyEntity.this.setTarget((LivingEntity)null);
	         super.tick();
	      }
	   }

	@Override
	public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected ResourceLocation getDefaultLootTable() {
		// TODO Auto-generated method stub
		return LOOT_TABLE;
	}
    public static boolean canSpawn(EntityType<ExampleEntity> entity, LevelAccessor levelAccess, MobSpawnType spawnType,
            BlockPos pos, Random random) {
        return checkAnimalSpawnRules(entity, levelAccess, spawnType, pos, random) && pos.getY() > 70;
    }
	}
