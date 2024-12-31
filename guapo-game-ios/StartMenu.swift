//
//  StartMenu.swift
//  SoloMission
//
//  Created by Derek Harrison on 30/12/2024.
//

import Foundation
import SpriteKit


class StartMenu: SKScene {
    
    let player_menu_np = SKSpriteNode(imageNamed: PLAYER_MENU_BUTTON_NOTPRESSED)
    let player_menu_p = SKSpriteNode(imageNamed: PLAYER_MENU_BUTTON_PRESSED)
    let level_menu_np = SKSpriteNode(imageNamed: LEVEL_MENU_NOTPRESSED)
    let level_menu_p = SKSpriteNode(imageNamed: LEVEL_MENU_PRESSED)
    
    var player_menu_touched = false
    var player_menu = false
    var player_menu_scene = false
    
    var level_menu_touched = false
    var level_menu = false
    var level_menu_scene = false
    
    override func didMove(to view: SKView) {
        let background = SKSpriteNode(imageNamed: BACKGROUND_START_SCREEN)
        
        let widthb = background.size.width
        let heightb = self.size.height/2
        
        background.size = CGSize(width: widthb, height: heightb)
        
        background.position = CGPoint(x: self.size.width / 2, y: self.size.height / 2)
        background.zPosition = 0
        self.addChild(background)
        
        place_button(fac: 0.6, button_np: player_menu_np, button_p: player_menu_p)
        place_button(fac: 0.46, button_np: level_menu_np, button_p: level_menu_p)
    }
    
    //Force the mute button to change by running update()
    override func update(_ currentTime: TimeInterval) {
   
        start_scene(scene: self, start : &player_menu, GameLevel : PlayerScene(size: self.size))
        start_scene(scene: self, start : &level_menu, GameLevel : MainMenuScene(size: self.size))
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        
        for touch: AnyObject in touches {
            
            let pointOfTouch = touch.location(in: self)
        
            touched_button(pointOfTouch: pointOfTouch, level_np : player_menu_np, level_p : player_menu_p, start_level_touched : &player_menu)
            touched_button(pointOfTouch: pointOfTouch, level_np : level_menu_np, level_p : level_menu_p, start_level_touched : &level_menu)
        }
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        if player_menu_touched {
            player_menu = true
            player_menu_scene = true
        }
        if level_menu_touched {
            level_menu = true
            level_menu_scene = true
        }
    }
    
    func place_button(fac : CGFloat, button_np : SKSpriteNode, button_p : SKSpriteNode) {
        
        let width_button = self.size.width / 5
        let height_button = self.size.height / 16
        
        button_np.size = CGSize(width: width_button, height: height_button)
        button_np.position = CGPoint(x: self.size.width / 2, y: self.size.height / 2 * fac + self.size.height / 4)
        button_np.zPosition = 2
        self.addChild(button_np)
        
        button_p.size = CGSize(width: width_button, height: height_button)
        button_p.position = CGPoint(x: self.size.width / 2, y: self.size.height / 2 * fac + self.size.height / 4)
        button_p.zPosition = -1
        self.addChild(button_p)
    }
    
    func touched_button(pointOfTouch : CGPoint, level_np : SKSpriteNode, level_p : SKSpriteNode, start_level_touched : inout Bool) {
        let touched_button_x = pointOfTouch.x > level_np.position.x - level_np.size.width / 2 && pointOfTouch.x < level_np.position.x + level_np.size.width / 2
        
        let touched_button_y = pointOfTouch.y > level_np.position.y - level_np.size.height / 2 && pointOfTouch.y < level_np.position.y + level_np.size.height / 2
        
        if touched_button_x && touched_button_y {
            level_np.zPosition = -1
            level_p.zPosition = 2
            start_level_touched = true
        }
    }
}
