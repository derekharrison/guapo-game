//
//  PlayerScene.swift
//  SoloMission
//
//  Created by Derek Harrison on 30/12/2024.
//

import Foundation
import SpriteKit

class PlayerScene: SKScene {
    
    let level_menu_np = SKSpriteNode(imageNamed: CONTINUE_NOTPRESSED)
    let level_menu_p = SKSpriteNode(imageNamed: CONTINUE_PRESSED)
    let start_menu_np = SKSpriteNode(imageNamed: START_MENU_NOTPRESSED)
    let start_menu_p = SKSpriteNode(imageNamed: START_MENU_PRESSED)
    
    let guapo_image_i = SKSpriteNode(imageNamed: GUAPO_IMAGE_PLAYER)
    let tutti_image_i = SKSpriteNode(imageNamed: TUTTI_IMAGE_PLAYER)

    let guapo_button_np = SKSpriteNode(imageNamed: GUAPO_BUTTON_NOTPRESSED)
    let guapo_button_p = SKSpriteNode(imageNamed: GUAPO_BUTTON_PRESSED)
    let tutti_button_np = SKSpriteNode(imageNamed: TUTTI_BUTTON_NOTPRESSED)
    let tutti_button_p = SKSpriteNode(imageNamed: TUTTI_BUTTON_PRESSED)
    
    var start_menu_touched = false
    var start_menu = false
    var start_menu_scene = false
    
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
        
        place_image(fac: 0.8, button: guapo_image_i)
        place_image(fac: 0.66, button: tutti_image_i)
        
        place_button(fac: 0.8, button_np: guapo_button_np, button_p: guapo_button_p)
        place_button(fac: 0.66, button_np: tutti_button_np, button_p: tutti_button_p)
        
        place_button2(fac: 0.3, button_np: level_menu_np, button_p: level_menu_p)
        place_button2(fac: 0.16, button_np: start_menu_np, button_p: start_menu_p)
    }
    
    //Force the mute button to change by running update()
    override func update(_ currentTime: TimeInterval) {
        start_scene(scene: self, start : &start_menu, GameLevel : StartMenu(size: self.size))
        start_scene(scene: self, start : &level_menu, GameLevel : MainMenuScene(size: self.size))
        
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        
        for touch: AnyObject in touches {
            
            let pointOfTouch = touch.location(in: self)
    
            var dummy1 = false
            var dummy2 = false
            
            touched_button(pointOfTouch: pointOfTouch, level_np : start_menu_np, level_p : start_menu_p, touched : &start_menu)
            touched_button(pointOfTouch: pointOfTouch, level_np : level_menu_np, level_p : level_menu_p, touched : &level_menu)
            touched_button2(pointOfTouch: pointOfTouch, level_np : guapo_button_np, level_p : guapo_button_p, touched : &dummy1)
            touched_button2(pointOfTouch: pointOfTouch, level_np : tutti_button_np, level_p : tutti_button_p, touched : &dummy2)
            
            if dummy1 {
                let defaults = UserDefaults()
                defaults.set(0, forKey: "player_id")
            }
            
            if dummy2 {
                let defaults = UserDefaults()
                defaults.set(1, forKey: "player_id")
            }
        }
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        if start_menu_touched {
            start_menu = true
            start_menu_scene = true
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
    
    func place_button2(fac : CGFloat, button_np : SKSpriteNode, button_p : SKSpriteNode) {
        
        let width_button = self.size.width / 4
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
    
    func place_image(fac : CGFloat, button : SKSpriteNode) {
        
        let width_button = self.size.width / 16
        let height_button = self.size.height / 16
        
        button.size = CGSize(width: width_button, height: height_button)
        button.position = CGPoint(x: self.size.width / 2 - self.guapo_image_i.size.width * 3, y: self.size.height / 2 * fac + self.size.height / 4)
        button.zPosition = 2
        self.addChild(button)
    }
    
    func touched_button(pointOfTouch : CGPoint, level_np : SKSpriteNode, level_p : SKSpriteNode, touched : inout Bool) {
    
        
        let touched_button_x = pointOfTouch.x > level_np.position.x - level_np.size.width / 2 && pointOfTouch.x < level_np.position.x + level_np.size.width / 2
        
        let touched_button_y = pointOfTouch.y > level_np.position.y - level_np.size.height / 2 && pointOfTouch.y < level_np.position.y + level_np.size.height / 2
        
        if touched_button_x && touched_button_y {
            
            level_np.zPosition = -1
            level_p.zPosition = 2
            touched = true
        }
    }
    
    func touched_button2(pointOfTouch : CGPoint, level_np : SKSpriteNode, level_p : SKSpriteNode, touched : inout Bool) {
    
        
        let touched_button_x = pointOfTouch.x > level_np.position.x - level_np.size.width / 2 && pointOfTouch.x < level_np.position.x + level_np.size.width / 2
        
        let touched_button_y = pointOfTouch.y > level_np.position.y - level_np.size.height / 2 && pointOfTouch.y < level_np.position.y + level_np.size.height / 2
        
        if touched_button_x && touched_button_y {
            self.guapo_button_np.zPosition = 2
            self.guapo_button_p.zPosition = -1
            self.tutti_button_np.zPosition = 2
            self.tutti_button_p.zPosition = -1
            
            level_np.zPosition = -1
            level_p.zPosition = 2
            touched = true
        }
    }
}
