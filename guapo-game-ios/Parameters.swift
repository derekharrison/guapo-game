//
//  Parameters.swift
//  SoloMission
//
//  Created by Derek Harrison on 25/09/2023.
//

import Foundation
import SpriteKit

let tot_num_birds = 12
var num_birds = 1
var num_jellyfish = 1
let num_cheesy_bites = 5
let num_cucumbers = 1
let num_paprikas = 1
let num_broccolis = 1
let num_beggin_strips = 1
let points_cheesy_bite = 1
let points_cucumber = 1
let points_paprika = 1
let points_broccoli = 3
let points_beggin_strip = 5
let num_frames_bird = 8
let num_frames_jelly = 15
let num_points_when_birds_appear = 35
var gameScore = 0
var muted = false
let num_frames_change = 5
var bound_tracker = 1
let num_screen_lengths_for_disp = 10
let points_at_which_beggin_strips_appear = 50
let num_frames_sun_popup = 180
let num_frames_flag_popup = 180
let bubble_vel_y = 5.0

let pause_button = SKSpriteNode(imageNamed: "pause_button_bitmap_cropped")
let play_button = SKSpriteNode(imageNamed: "play_button_bitmap_cropped")
let sun_popup_spr = SKSpriteNode(imageNamed: "sun_popup_bitmap_cropped")

let scoreLabel = SKLabelNode(fontNamed: "Courier-Bold")
let background_overlap = 10

let z_pos_player : CGFloat = CGFloat(tot_num_birds) * 5
let z_pos_snacks : CGFloat = CGFloat(tot_num_birds) * 2
let z_pos_chars : CGFloat = CGFloat(tot_num_birds) * 1
let z_pos_pause : CGFloat = CGFloat(tot_num_birds) * 5 + 1
let z_pos_continue : CGFloat = CGFloat(tot_num_birds) * 5 + 2
let z_pos_restart : CGFloat = CGFloat(tot_num_birds) * 5 + 2
let z_pos_lives : CGFloat = CGFloat(tot_num_birds) * 5 + 2
let z_pos_flag : CGFloat = CGFloat(tot_num_birds) * 5 - 1
let z_pos_sun : CGFloat = CGFloat(tot_num_birds) * 5 - 1
let z_pos_black : CGFloat = CGFloat(tot_num_birds) * 10

let min_z_pos_jelly_fish = CGFloat(tot_num_birds) * 3
let min_z_pos_fishes = CGFloat(tot_num_birds) * 1
let min_z_pos_birds = min_z_pos_jelly_fish

let bulletSound = SKAction.playSoundFileNamed("sound_effect.wav", waitForCompletion: true)
let endSound = SKAction.playSoundFileNamed("tutti_0.wav", waitForCompletion: true)
let eat_sound1 = SKAction.playSoundFileNamed("tutti_eating_knaagstok.wav", waitForCompletion: true)
let eat_sound2 = SKAction.playSoundFileNamed("tutti_eating_tosti.wav", waitForCompletion: true)
let eat_sound3 = SKAction.playSoundFileNamed("tutti_eating_pathe.wav", waitForCompletion: true)
let frito_sound = SKAction.playSoundFileNamed("tutti_3.wav", waitForCompletion: true)
let brownie_sound = SKAction.playSoundFileNamed("tutti_6.wav", waitForCompletion: true)
let misty_sound = SKAction.playSoundFileNamed("tutti_1.wav", waitForCompletion: true)
let frito_sound_appearing = SKAction.playSoundFileNamed("cat_sound1.wav", waitForCompletion: true)
let brownie_sound_appearing = SKAction.playSoundFileNamed("tutti_5.wav", waitForCompletion: true)
let misty_sound_appearing = SKAction.playSoundFileNamed("misty_sounds.wav", waitForCompletion: true)
let sun_popup_sound = SKAction.playSoundFileNamed("sun_popup_sound.wav", waitForCompletion: true)
let bubbles_sounds = SKAction.playSoundFileNamed("bubble_sounds.wav", waitForCompletion: false)
let blowfish_sound = SKAction.playSoundFileNamed("tutti_4.wav", waitForCompletion: false)

let FRITO_IMAGE_1 = "frito_bitmap_cropped"
let FRITO_IMAGE_2 = "frito_bitmap_rotated_cropped"
let FRITO_OCEAN_1 = "frito_snorkel_bitmap_cropped"
let FRITO_OCEAN_2 = "frito_snorkel_hit_bitmap_rotated_cropped"

let BROWNIE_IMAGE_1 = "brownie1_bitmap_cropped"
let BROWNIE_IMAGE_2 = "brownie2_bitmap_cropped"
let BROWNIE_OCEAN_1 = "brownie_snorkel_bitmap_cropped"
let BROWNIE_OCEAN_2 = "brownie_snorkel_bitmap_hit_cropped"

let MISTY_IMAGE_1 = "misty_bitmap_cropped"
let MISTY_IMAGE_2 = "misty_hit_bitmap_cropped"
let MISTY_IMAGE_3 = "misty_bitmap_cropped_rotated"
let MISTY_IMAGE_4 = "misty_hit_bitmap_cropped_rotated"
let MISTY_OCEAN_1 = "misty_withsnorkel_bitmap_cropped"
let MISTY_OCEAN_2 = "misty_withsnorkel_hit_bitmap_cropped"
let MISTY_OCEAN_3 = "misty_withsnorkel_bitmap_cropped_rotated"
let MISTY_OCEAN_4 = "misty_withsnorkel_hit_bitmap_cropped_rotated"

let PLAYER_IMAGE_1 = "guapo_1_bitmap_cropped"
let PLAYER_IMAGE_2 = "guapo_2_bitmap_cropped"
let PLAYER_IMAGE_HIT = "guapo_3_bitmap_cropped"
let PLAYER_SNORKEL = "guapo_snorkelandscubatank_unhit"
let PLAYER_SNORKEL_HIT = "guapo_snorkelandscubatank_hit"

let BIRD_IMAGE_WARA_1 = "warawara1_bitmap_custom_mod_cropped"
let BIRD_IMAGE_WARA_2 = "warawara2_bitmap_custom_mod_cropped"
let BIRD_IMAGE_WARA_3 = "warawara3_bitmap_custom_mod_cropped"

let BIRD_IMAGE_SEAGULL_1 = "seagull1_bitmap_cropped_new"
let BIRD_IMAGE_SEAGULL_2 = "seagull2_bitmap_cropped_new"
let BIRD_IMAGE_SEAGULL_3 = "seagull3_bitmap_cropped_new"

let JELLY_IMAGE_1 = "jelly_fish_bitmap_cropped1"
let JELLY_IMAGE_2 = "jelly_fish_bitmap_cropped2"
let JELLY_IMAGE_3 = "jelly_fish_bitmap_cropped3"

let FISH_IMAGE_1 = "fish5_bitmap_cropped"
let FISH_IMAGE_2 = "fish6_bitmap_cropped"

let FISH_IMAGE_2A = "fish3_bitmap_cropped"
let FISH_IMAGE_2B = "fish4_bitmap_cropped"

let FISH_IMAGE_3A = "fish8_bitmap_cropped"
let FISH_IMAGE_3B = "fish9_bitmap_cropped"

let FISH_IMAGE_4A = "fish14b_bitmap_cropped_resized_purple"
let FISH_IMAGE_4B = "fish14bb_bitmap_cropped_resized_purple"

let FISH_IMAGE_5A = "fish12_bitmap_cropped"
let FISH_IMAGE_5B = "fish12b_bitmap_cropped"

let FISH_IMAGE_6A = "yellowfish_facingright_raw"
let FISH_IMAGE_6B = "yellowfish_facingright_raw2"

let BLOW_FISH_IMAGE_1 = "fish10_bitmap_cropped"
let BLOW_FISH_IMAGE_2 = "fish10b_bitmap_cropped"
let BLOW_FISH_IMAGE_3 = "fish11_bitmap_cropped"
let BLOW_FISH_IMAGE_4 = "fish11b_bitmap_cropped"

let CHEESY_BITE_IMAGE = "cheesy_bite_resized"
let PAPRIKA_IMAGE = "paprika_bitmap_cropped"
let BROCCOLI_IMAGE = "broccoli_bitmap_cropped"
let CUCUMBER_IMAGE = "cucumber_bitmap_cropped"
let BEGGIN_IMAGE = "beggin_strip_cropped"

let BACKGROUND_STR_LEVEL_1 = "background_guapo_game_nr"
let BACKGROUND_STR_LEVEL_2 = "beach_background_slide"
let BACKGROUND_STR_LEVEL_3 = "background_guapo_game_level3_"
let BACKGROUND_STR_LEVEL_4 = "background_guapogame_underwaterlevel_"
let BACKGROUND_STR_LEVEL_5 = "background_tuttigame_nr"
let BACKGROUND_OPAQUE_STR = "black_background"

let NUM_BACKGROUNDS_LEVEL_1 = 10
let NUM_BACKGROUNDS_LEVEL_2 = 14
let NUM_BACKGROUNDS_LEVEL_3 = 11
let NUM_BACKGROUNDS_LEVEL_4 = 5
let NUM_BACKGROUNDS_LEVEL_5 = 18

let BUBBLE_IMAGE_STR = "bubble_bitmap_cropped"

let HIGH_SCORE_ID_1 = "HighScoreLevel1Saved"
let HIGH_SCORE_ID_2 = "HighScoreLevel2Saved"
let HIGH_SCORE_ID_3 = "HighScoreLevel3Saved"
let HIGH_SCORE_ID_4 = "HighScoreLevel4Saved"
let HIGH_SCORE_ID_5 = "HighScoreLevel5Saved"

let LEVEL_ID_1 = 1
let LEVEL_ID_2 = 2
let LEVEL_ID_3 = 3
let LEVEL_ID_4 = 4
let LEVEL_ID_5 = 5

let GAME_MUTED = "gameMuted"
let PLAYING = "playing"
let GAME_FONT = "AvenirNext-Bold"
let LABEL_FONT = "Helvetica"

let BACKGROUND_START_SCREEN = "background_guapo_game_startscreen2"

let LEVELS_STR = "Levels"
let HIGHSCORE_STR = "High Score"
let SOUND_ON_IMAGE_STR = "volume_on"
let SOUND_OFF_IMAGE_STR = "volume_off"

let MAIN_MENU_BUTTON_NOTPRESSED_1 = "aruba_level_button_not_pressed_bitmap_cropped"
let MAIN_MENU_BUTTON_PRESSED_1 = "aruba_level_button_pressed_bitmap_cropped"
let MAIN_MENU_BUTTON_NOTPRESSED_2 = "beach_level_button_not_pressed_bitmap_cropped"
let MAIN_MENU_BUTTON_PRESSED_2 = "beach_level_button_pressed_bitmap_cropped"
let MAIN_MENU_BUTTON_NOTPRESSED_3 = "trip_level_button_not_pressed_bitmap_cropped"
let MAIN_MENU_BUTTON_PRESSED_3 = "trip_level_button_pressed_bitmap_cropped"
let MAIN_MENU_BUTTON_NOTPRESSED_4 = "ocean_level_button_not_pressed_bitmap_cropped"
let MAIN_MENU_BUTTON_PRESSED_4 = "ocean_level_button_pressed_bitmap_cropped"
let MAIN_MENU_BUTTON_NOTPRESSED_5 = "utreg_level_button_not_pressed_bitmap_cropped"
let MAIN_MENU_BUTTON_PRESSED_5 = "utreg_level_button_pressed_bitmap_cropped"

let MAIN_MENU_BUTTON_GRAY_2 = "beach_level_button_grey"
let MAIN_MENU_BUTTON_GRAY_3 = "trip_level_button_grey"
let MAIN_MENU_BUTTON_GRAY_4 = "ocean_level_button_grey"
let MAIN_MENU_BUTTON_GRAY_5 = "utreg_level_button_grey"
let LEVEL_UNLOCK_GUARD = 100
let FLAG_FREQUENCY = 100

let BACKGROUNDS_STR = "backgrounds"
let PLAYER_STR = "LEVEL_1_player"
let BROWNIE_STR = "LEVEL_1_brownie"
let FRITO_STR = "LEVEL_1_frito"
let MISTY_STR = "LEVEL_1_misty"
let CHEESY_STR = "LEVEL_1_CHEESY"
let PAPRIKA_STR = "LEVEL_1_PAPRIKA"
let CUCUMBER_STR = "LEVEL_1_CUCUMBERS"
let BEGGIN_STR = "LEVEL_1_BEGGIN"
let BROCCOLI_STR = "LEVEL_1_BROCCOLIS"

let FISH_STR_1 = "FISH_1"
let FISH_STR_2 = "FISH_2"
let FISH_STR_3 = "FISH_3"
let FISH_STR_4 = "FISH_4"
let FISH_STR_5 = "FISH_5"
let FISH_STR_6 = "FISH_6"
let BLOWFISH_STR = "BLOWFISH"
let HEART_IMAGE_STR = "heart1_bitmap_cropped"
let SCORE_ID = "SCORE_ID"
let MISTY_GUARD = "MISTY_GUARD"
let FLAG_NUM = "FLAG_NUM"
let NUM_LIVES_STR = "NUM_LIVES"
let NUM_LIVES = 3

let FLAG_ARUBA_STR = "flag_aruba_bitmap_cropped"
let FLAG_NETHERLANDS_STR = "flag_netherlands_bitmap_cropped"

let CONTINUE_BUTTON_NOT_PRESSED = "continue_button_not_pressed_bitmap_cropped"
let CONTINUE_BUTTON_PRESSED = "continue_button_pressed_bitmap_cropped"
let RESTART_BUTTON_NOT_PRESSED = "restart_button_not_pressed_bitmap_cropped"
let RESTART_BUTTON_PRESSED = "restart_button_pressed_bitmap_cropped"
