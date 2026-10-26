package com.example.data

import com.example.model.FormulaCard
import com.example.model.McqQuestion
import com.example.model.TopicNote
import com.example.model.VideoResource

object ChemistryRepository {

    val sampleNotes: List<TopicNote> = listOf(
        TopicNote(
            id = "note_aromaticity",
            unit = "Organic Chemistry",
            title = "Aromaticity & Hückel's Rule",
            hindiTitle = "एरोमैटिकता एवं हकल का नियम",
            summary = "Planarity, continuous conjugation, and (4n+2) π electrons criteria for aromaticity vs anti-aromaticity.",
            body = """
1. Aromaticity Criteria (Hückel's 4n + 2 Rule)
A monocyclic, planar, fully conjugated ring system with (4n + 2) delocalized π electrons exhibits extraordinary thermodynamic resonance stability.
किसी एकल-चक्रीय, समतलीय और पूर्णतः संयुग्मित निकाय में (4n + 2) विस्थानीकृत π इलेक्ट्रॉन होने पर वह असाधारण ऊष्मागतिक स्थायित्व प्रदर्शित करता है।

• Cyclic (चक्रीय): Ring with continuous overlapping p-orbitals.
• Planar (समतलीय): sp² hybridized carbons or equivalent geometry allowing p-orbital parallel overlap.
• Conjugated (संयुग्मित): Alternating double-single bonds or unhybridized p-orbitals.
• Electron Count (इलेक्ट्रॉन गणना):
   - Aromatic: (4n + 2) π electrons (n = 0, 1, 2, 3... -> 2, 6, 10, 14, 18 π e⁻)
   - Anti-aromatic: 4n π electrons (n = 1, 2, 3... -> 4, 8, 12, 16 π e⁻ in planar cyclic conjugated geometry, highly destabilized).
   - Non-aromatic: Fails planarity, non-cyclic, or breaks continuous conjugation (e.g. cyclooctatetraene in tub conformation has 8 π e⁻ but is non-aromatic).

2. Key Annulenes & Ions in CSIR-NET:
• Cyclopropenyl cation: 2 π e⁻ (n=0) -> Aromatic.
• Cyclopentadienyl anion (Cp⁻): 6 π e⁻ (n=1) -> Aromatic, pKa ≈ 16 (very acidic cyclopentadiene).
• Tropylium cation (C7H7⁺): 6 π e⁻ (n=1) -> Aromatic and very stable.
• Cyclooctatetraene (COT, [8]annulene): 8 π e⁻. Adopts tub conformation to avoid anti-aromaticity, behaving as non-aromatic polyene. However, COT²⁻ (dianion) has 10 π e⁻ and becomes planar aromatic!
• [10]Annulene: Trans,cis isomers suffer transannular steric clash of inner hydrogens, causing non-planar geometry (non-aromatic) unless bridged (e.g. 1,6-methano[10]annulene is aromatic).
• [14]Annulene and [18]Annulene: [18]annulene has 18 π e⁻ (n=4), planar aromatic, showing distinct inner 6H (shielded, δ -3.0 ppm) and outer 12H (deshielded, δ 9.3 ppm) in 1H-NMR!
""".trimIndent(),
            keyFormulas = listOf(
                "Aromatic π electrons = 4n + 2 (n = 0, 1, 2, 3...)",
                "Anti-aromatic π electrons = 4n (n = 1, 2, 3...)",
                "Diamagnetic ring current = Outer protons strongly deshielded (δ > 7 ppm), inner protons shielded (δ < 0 ppm)"
            ),
            examTips = listOf(
                "CSIR-NET frequently asks about [8]annulene dianion (COT²⁻): it is 10 π e⁻ aromatic with D8h planar geometry.",
                "Always check for heteroatom lone pair contribution: In Pyrrole, lone pair is in p-orbital (part of 6 π e⁻ aromatic sextet); in Pyridine, lone pair is in sp² hybrid orbital (not part of π system, basic)."
            )
        ),
        TopicNote(
            id = "note_cft",
            unit = "Inorganic Chemistry",
            title = "Crystal Field Theory (CFT) & Splitting",
            hindiTitle = "क्रिस्टल क्षेत्र सिद्धांत एवं विभाजन",
            summary = "Octahedral (t2g / eg) & Tetrahedral (e / t2) splitting, CFSE calculation, high-spin vs low-spin.",
            body = """
1. Octahedral Field Splitting (Δo):
In an octahedral coordination environment [ML6], six ligands approach along the Cartesian axes (x, y, z).
अष्टफलकीय संकुल [ML6] में 6 लिगैंड अक्षों (x, y, z) के अनुदिश आते हैं।

• Axial d-orbitals (dx²-y², dz²) point directly at ligands and experience maximum electrostatic repulsion, splitting into higher energy eg level (+0.6 Δo or +6 Dq).
• Non-axial d-orbitals (dxy, dyz, dxz) point between axes, experiencing less repulsion, splitting into lower energy t2g level (-0.4 Δo or -4 Dq).

2. Tetrahedral Field Splitting (Δt):
In a tetrahedral environment [ML4], ligands approach between axes.
• e set (dx²-y², dz²) is lower in energy (-0.6 Δt).
• t2 set (dxy, dyz, dxz) is higher in energy (+0.4 Δt).
• Relation: Δt ≈ (4/9) Δo. Because Δt is almost always smaller than the pairing energy (P), tetrahedral complexes are virtually always high-spin!

3. Crystal Field Stabilization Energy (CFSE):
For octahedral complex:
CFSE = [-0.4 · n(t2g) + 0.6 · n(eg)] Δo + m · P
(where n is electron count, m is number of extra pairs formed beyond free ion).

• High-spin vs Low-spin:
  If Δo > P (strong field ligand, e.g. CN⁻, CO), low-spin configuration is favored.
  If Δo < P (weak field ligand, e.g. I⁻, Br⁻, F⁻), high-spin configuration is favored.

4. Jahn-Teller Distortion (J-T Effect):
Any non-linear molecular system in a spatially degenerate electronic state will distort to lower its symmetry and remove degeneracy.
• Strong J-T distortion occurs with asymmetrically filled eg orbitals:
  - d⁴ high-spin (t2g³ eg¹)
  - d⁹ (t2g⁶ eg³, e.g. Cu²⁺ complexes: tetragonally elongated octahedral geometry with 2 long axial bonds and 4 short equatorial bonds).
• Weak or negligible J-T distortion occurs with asymmetric t2g filling.
""".trimIndent(),
            keyFormulas = listOf(
                "Octahedral CFSE = [-0.4 · n(t2g) + 0.6 · n(eg)] Δo + m·P",
                "Tetrahedral CFSE = [-0.6 · n(e) + 0.4 · n(t2)] Δt",
                "Δt ≈ 4/9 Δo",
                "Spectrochemical Series: I⁻ < Br⁻ < S²⁻ < SCN⁻ < Cl⁻ < NO3⁻ < F⁻ < OH⁻ < H2O < NCS⁻ < NH3 < en < bpy < phen < NO2⁻ < PPh3 < CN⁻ < CO"
            ),
            examTips = listOf(
                "Cu(II) (d⁹) complexes almost always exhibit z-out tetragonal elongation due to Jahn-Teller effect.",
                "d³ and d⁸ octahedral complexes have the highest thermodynamic stability (CFSE = -1.2 Δo)."
            )
        ),
        TopicNote(
            id = "note_kinetics",
            unit = "Physical Chemistry",
            title = "Chemical Kinetics & Reaction Dynamics",
            hindiTitle = "रासायनिक गतिकी एवं अभिक्रिया गतिशीलता",
            summary = "Integrated rate laws, half-lives, steady-state approximation, Arrhenius & Eyring transition state theory.",
            body = """
1. Rate Laws and Integrated Equations:
The order of a reaction is experimentally determined and indicates the concentration dependency of the rate.
अभिक्रिया की कोटि (order) प्रायोगिक रूप से निर्धारित होती है।

• Zero Order:
  Rate = k [A]⁰ = k
  [A]t = [A]0 - kt
  t1/2 = [A]0 / (2k) (Half-life is directly proportional to initial concentration!)
  Unit of k: mol L⁻¹ s⁻¹

• First Order:
  Rate = k [A]
  ln([A]0 / [A]t) = kt  or  [A]t = [A]0 · e^(-kt)
  t1/2 = ln(2) / k = 0.693 / k (Independent of initial concentration!)
  Unit of k: s⁻¹

• Second Order (2A -> Products):
  Rate = k [A]²
  1/[A]t - 1/[A]0 = kt
  t1/2 = 1 / (k [A]0) (Inversely proportional to initial concentration!)
  Unit of k: L mol⁻¹ s⁻¹

2. Steady State Approximation (SSA):
For unstable reactive intermediates (I), rate of formation equals rate of consumption:
d[I]/dt ≈ 0.
This allows deriving complex rate laws (e.g. Lindemann-Hinshelwood mechanism for unimolecular reactions).

3. Temperature Dependence (Arrhenius & Eyring):
• Arrhenius Equation: k = A · exp(-Ea / RT)
  ln(k) = ln(A) - Ea / (RT)
  Plot of ln(k) vs 1/T yields slope = -Ea / R.

• Eyring Equation (Transition State Theory):
  k = (kB · T / h) · exp(ΔS‡ / R) · exp(-ΔH‡ / RT)
  Relation: Ea = ΔH‡ + RT (for solution reactions).
  Large negative ΔS‡ implies an associative transition state (ordered transition state, ring formation).
  Positive ΔS‡ implies a dissociative transition state (bond cleavage, increased disorder).
""".trimIndent(),
            keyFormulas = listOf(
                "Zero Order: t1/2 = [A]0 / (2k)",
                "First Order: t1/2 = 0.693 / k ; ln([A]0/[A]t) = kt",
                "Second Order: t1/2 = 1 / (k[A]0) ; 1/[A]t - 1/[A]0 = kt",
                "Arrhenius: ln(k2/k1) = (Ea/R) · [(T2 - T1) / (T1·T2)]",
                "Eyring Relation: Ea = ΔH‡ + n·R·T (n=1 for solution/unimolecular)"
            ),
            examTips = listOf(
                "Check unit of k to quickly find order: k has units of (mol/L)^(1-n) · time⁻¹ for order n.",
                "For parallel reactions A -> B (k1) and A -> C (k2), the overall rate constant k = k1 + k2, and product ratio [B]/[C] = k1/k2."
            )
        ),
        TopicNote(
            id = "note_spectroscopy",
            unit = "Spectroscopy",
            title = "Beer–Lambert Law & UV-Vis Spectroscopy",
            hindiTitle = "बीयर–लैम्बर्ट नियम एवं पराबैंगनी स्पेक्ट्रोस्कोपी",
            summary = "Absorbance equation, molar absorptivity, Woodward-Fieser rules for conjugated dienes and enones.",
            body = """
1. Beer-Lambert Law:
When monochromatic light passes through an absorbing medium of path length l (cm) and concentration c (mol/L):
जब एकवर्णी प्रकाश l लंबाई और c सांद्रता वाले माध्यम से गुजरता है:

Transmittance: T = I / I0
Absorbance (Optical Density):
A = -log10(T) = log10(I0 / I) = ε · l · c

• A: Absorbance (dimensionless)
• ε: Molar absorptivity / molar extinction coefficient (L mol⁻¹ cm⁻¹)
• l: Path length (typically 1 cm in quartz cuvettes)
• c: Concentration (mol L⁻¹)

Limitations: Deviations occur at high concentrations (> 0.01 M) due to electrostatic interactions, scattering, chemical equilibria (dimerization, ionization), or polychromatic radiation.

2. Electronic Transitions in UV-Vis:
• σ -> σ* (Vacuum UV, < 150 nm, e.g. alkanes)
• n -> σ* (150 - 250 nm, e.g. alcohols, amines, ethers)
• π -> π* (High intensity, allowed transition, ε > 10,000, e.g. alkenes, aromatics)
• n -> π* (Forbidden by symmetry, lower intensity, ε = 10 - 100, e.g. carbonyls at ~280 nm)

3. Woodward-Fieser Rules (Conjugated Dienes):
• Base value for heteroannular diene: 214 nm
• Base value for homoannular diene: 253 nm
• Base value for acyclic butadiene: 217 nm
• Increments:
  - Extended conjugation: +30 nm
  - Each alkyl substituent / ring residue: +5 nm
  - Exocyclic double bond: +5 nm
  - -OAc: 0 nm, -OR: +6 nm, -SR: +30 nm, -Cl, -Br: +5 nm
""".trimIndent(),
            keyFormulas = listOf(
                "A = ε · l · c",
                "A = 2 - log10(%T)",
                "Heteroannular diene base = 214 nm",
                "Homoannular diene base = 253 nm",
                "Extended conjugation = +30 nm per double bond",
                "Ring residue / alkyl substituent = +5 nm each"
            ),
            examTips = listOf(
                "Homoannular diene: both conjugated double bonds are located in the same ring (cisoid, s-cis) -> base 253 nm.",
                "Exocyclic double bond: a double bond attached directly to a ring vertex -> add +5 nm."
            )
        ),
        TopicNote(
            id = "note_nmr",
            unit = "Spectroscopy",
            title = "1H & 13C NMR Spectroscopy",
            hindiTitle = "1H तथा 13C एनएमआर स्पेक्ट्रोस्कोपी",
            summary = "Chemical shifts (δ), spin-spin coupling (J), Karplus equation, DEPT-45/90/135 interpretation.",
            body = """
1. 1H-NMR Fundamentals:
Protons in different chemical environments resonate at distinct chemical shifts (δ in ppm relative to TMS at 0 ppm).
विभिन्न रासायनिक वातावरणों में प्रोटॉन अलग-अलग रासायनिक शिफ्ट प्रदर्शित करते हैं।

• Alkanes (CH3, CH2, CH): δ 0.8 - 1.8 ppm
• Protons α to carbonyl (-CH2-C=O): δ 2.1 - 2.6 ppm
• Protons α to heteroatom (-CH2-O, -CH2-X): δ 3.2 - 4.2 ppm
• Alkyne protons (C≡C-H): δ 2.0 - 3.0 ppm (shielded by anisotropic cylindrical π cloud)
• Alkene protons (=C-H): δ 4.5 - 6.5 ppm
• Aromatic protons (Ar-H): δ 6.5 - 8.5 ppm (strongly deshielded by ring current)
• Aldehydic proton (-CHO): δ 9.0 - 10.0 ppm
• Carboxylic acid proton (-COOH): δ 10.5 - 12.5 ppm

2. Spin-Spin Splitting (n + 1 rule):
• Coupling constant J is independent of applied magnetic field strength B0 (unlike chemical shift in Hz).
• Trans-alkene protons: J_trans ≈ 12 - 18 Hz (large)
• Cis-alkene protons: J_cis ≈ 6 - 12 Hz
• Geminal protons: J_gem ≈ 0 - 3 Hz (sp²) or 12 - 15 Hz (sp³)
• Ortho Ar-H: J_ortho ≈ 7 - 9 Hz ; J_meta ≈ 2 - 3 Hz ; J_para ≈ 0 - 1 Hz

3. 13C DEPT Experiments (Distortionless Enhancement by Polarization Transfer):
• DEPT-45: CH3, CH2, CH all point UP.
• DEPT-90: ONLY CH carbons point UP (CH3 and CH2 vanish).
• DEPT-135: CH3 and CH point UP, CH2 points DOWN (inverted)! Quaternary carbons (C) are absent in all DEPT spectra!
""".trimIndent(),
            keyFormulas = listOf(
                "Chemical shift δ (ppm) = (ν_sample - ν_TMS) (Hz) / Spectrometer frequency (MHz)",
                "Splitting multiplicity = 2nI + 1 (for I = 1/2, multiplicity = n + 1)",
                "Pascal's triangle gives peak intensity ratios: Singlet (1), Doublet (1:1), Triplet (1:2:1), Quartet (1:3:3:1)",
                "DEPT-135: CH3 (+) , CH (+) , CH2 (-) , Quaternary (absent)"
            ),
            examTips = listOf(
                "DEPT-135 is the fastest way in CSIR-NET Part C to identify CH2 groups (they appear inverted/negative).",
                "Look for symmetry: identical homotopic or enantiotopic protons give single combined peaks."
            )
        ),
        TopicNote(
            id = "note_pericyclic",
            unit = "Pericyclic Reactions",
            title = "Pericyclic Reactions & Woodward-Hoffmann Rules",
            hindiTitle = "पेरीसाइक्लिक अभिक्रियाएँ एवं वुडवर्ड-हॉफमैन नियम",
            summary = "Electrocyclic, cycloadditions, sigmatropic shifts under thermal (Δ) and photochemical (hν) conditions.",
            body = """
1. Electrocyclic Reactions:
Reversible interconversion of conjugated polyenes and cyclic alkenes.
• 4n π systems (e.g. Butadiene, 4 π e⁻):
  - Thermal (Δ): Conrotatory (con)
  - Photochemical (hν): Disrotatory (dis)

• (4n + 2) π systems (e.g. 1,3,5-Hexatriene, 6 π e⁻):
  - Thermal (Δ): Disrotatory (dis)
  - Photochemical (hν): Conrotatory (con)

Mnemonic: 'ODC'
- Opposite substituents (trans/trans or cis/cis) + Disrotatory -> Cis product
- Opposite substituents + Conrotatory -> Trans product

2. Cycloaddition Reactions:
• [4 + 2] Diels-Alder:
  - 6 π e⁻ -> Thermally allowed via [4s + 2s] suprafacial pathway.
  - Stereospecific: Syn addition (stereochemistry of dienophile and diene preserved).
  - Endo Rule (Alder's rule): Secondary orbital interaction between electron-withdrawing groups of dienophile and diene π system stabilizes endo transition state.
• [2 + 2] Cycloaddition:
  - 4 π e⁻ -> Photochemically allowed [2s + 2s]; thermally forbidden [2s + 2s].

3. Sigmatropic Rearrangements:
Migration of a σ-bond flanked by one or more π systems.
• [3,3]-Sigmatropic Shift:
  - Cope Rearrangement: 1,5-hexadiene derivative via chair-like transition state.
  - Claisen Rearrangement: Allyl vinyl ether -> γ,δ-unsaturated carbonyl compound.
  - Oxy-Cope & Anionic Oxy-Cope: Accelerated by factor of 10¹⁰-10¹⁷ with base (KH/18-crown-6)!
""".trimIndent(),
            keyFormulas = listOf(
                "4n π : Δ = Conrotatory | hν = Disrotatory",
                "(4n+2) π : Δ = Disrotatory | hν = Conrotatory",
                "[4s + 2s] Diels-Alder = Thermally allowed, photochemical forbidden",
                "[2s + 2s] Cycloaddition = Photochemical allowed, thermal forbidden",
                "Suprafacial / Antarafacial selection: 4q+2 thermally suprafacial; 4q photochemically suprafacial"
            ),
            examTips = listOf(
                "Anionic Oxy-Cope rearrangement (alkoxide enolate) proceeds at room temperature due to huge rate enhancement.",
                "In Diels-Alder reactions, electron-donating groups (EDG) on diene and electron-withdrawing groups (EWG) on dienophile give fastest rates."
            )
        ),
        TopicNote(
            id = "note_organometallic",
            unit = "Inorganic Chemistry",
            title = "Organometallics & The 18-Electron Rule",
            hindiTitle = "कार्बधात्विक रसायन एवं 18-इलेक्ट्रॉन नियम",
            summary = "Electron counting (neutral vs ionic), oxidative addition, reductive elimination, migratory insertion.",
            body = """
1. The 18-Electron Rule:
Transition metal complexes tend to achieve noble gas electron configuration (18 valence electrons: ns² (n-1)d¹⁰ np⁶).
संक्रमण धातु संकुल 18 संयोजी इलेक्ट्रॉन विन्यास प्राप्त करने का प्रयास करते हैं।

Neutral / Covalent Method of Electron Counting:
• Metal: Group number (e.g. Fe = 8, Co = 9, Ni = 10, Rh = 9, Ru = 8)
• 1-electron ligands: -H, -CH3, -Cl, -Br, -I, -Ph, -CN (neutral radicals)
• 2-electron ligands: CO, PR3, PPh3, NH3, H2O, alkene (η²), carbenes (Fischer)
• 3-electron ligands: η³-allyl, -NO (bent)
• 4-electron ligands: η⁴-butadiene, chelating diphosphines (dppe)
• 5-electron ligands: η⁵-Cp (cyclopentadienyl)
• 6-electron ligands: η⁶-benzene, η⁶-arene
• Metal-Metal single bond (M-M): contributes 1 e⁻ to each metal

2. Fundamental Organometallic Steps:
• Oxidative Addition: Metal oxidation state increases by +2, coordination number increases by +2, electron count increases by +2 (e.g. Rh(I) 16 e⁻ + H2 -> Rh(III) 18 e⁻).
• Reductive Elimination: Reverse of oxidative addition. Requires cis-disposed ligands. Electron count decreases by 2, oxidation state decreases by 2.
• Migratory Insertion: A cis ligand (e.g. CO) inserts into an adjacent M-alkyl bond forming an M-acyl group. Oxidation state unchanged, electron count decreases by 2.
• β-Hydride Elimination: Requires empty coordination site (16 e⁻) and syn-coplanar M-C-C-H geometry.

3. Catalytic Cycles in CSIR-NET:
• Wilkinson's Catalyst: [RhCl(PPh3)3] for homogeneous alkene hydrogenation.
• Wacker Process: PdCl2 / CuCl2 / O2 for ethylene oxidation to acetaldehyde.
• Hydroformylation (Oxo Process): Co or Rh catalysts converting alkene + CO + H2 into aldehydes.
• Monsanto & Cativa Process: Methanol carbonylation to acetic acid using Rh or Ir catalysts.
""".trimIndent(),
            keyFormulas = listOf(
                "Total Valence Electrons (TVE) = Metal Group # + Ligand Electrons ± Charge + (M-M bonds × 2)",
                "Number of M-M bonds = (18 × n - TVE) / 2 (where n = number of metal atoms)",
                "Oxidative Addition: Δ(O.S.) = +2, Δ(C.N.) = +2, Δ(e⁻) = +2",
                "Reductive Elimination: Δ(O.S.) = -2, Δ(C.N.) = -2, Δ(e⁻) = -2"
            ),
            examTips = listOf(
                "Fe3(CO)12 has 48 TVE -> (18×3 - 48)/2 = 3 M-M bonds. It has 2 bridging COs.",
                "Square planar d⁸ complexes (e.g. Vaska's complex [IrCl(CO)(PPh3)2], 16 e⁻) undergo oxidative addition very readily."
            )
        ),
        TopicNote(
            id = "note_thermo",
            unit = "Physical Chemistry",
            title = "Chemical Thermodynamics & Maxwell Relations",
            hindiTitle = "ऊष्मागतिकी एवं मैक्सवेल संबंध",
            summary = "Fundamental thermodynamic potentials (U, H, A, G), Maxwell square, entropy and spontaneity.",
            body = """
1. Fundamental Thermodynamic Equations:
Four fundamental thermodynamic potentials based on 1st and 2nd Laws:
• Internal Energy: dU = T dS - P dV
• Enthalpy: dH = T dS + V dP
• Helmholtz Free Energy: dA = -S dT - P dV
• Gibbs Free Energy: dG = -S dT + V dP

2. Maxwell Thermodynamic Square (Mnemonic):
"Good Physicists Have Studied Under Very Active Teachers"
G   P   H
T       S
A   V   U

Maxwell Relations:
1. (∂T/∂V)_S = -(∂P/∂S)_V
2. (∂T/∂P)_S = (∂V/∂S)_P
3. (∂S/∂V)_T = (∂P/∂T)_V
4. (∂S/∂P)_T = -(∂V/∂T)_P

3. Gibbs Free Energy (ΔG) & Spontaneity:
At constant temperature and pressure:
ΔG = ΔH - T ΔS
• ΔG < 0: Spontaneous (exergonic process)
• ΔG = 0: System at chemical equilibrium
• ΔG > 0: Non-spontaneous (endergonic, spontaneous in reverse direction)

Relation with Equilibrium Constant K:
ΔG° = -RT ln(K_eq)
van 't Hoff Equation:
d(ln K)/dT = ΔH° / (R T²)
ln(K2 / K1) = -(ΔH°/R) · [1/T2 - 1/T1]

For exothermic reactions (ΔH° < 0): K decreases as T increases.
For endothermic reactions (ΔH° > 0): K increases as T increases.
""".trimIndent(),
            keyFormulas = listOf(
                "ΔG = ΔH - T ΔS",
                "ΔG° = -RT ln(K)",
                "dG = V dP - S dT",
                "(∂G/∂P)_T = V  and  (∂G/∂T)_P = -S",
                "Gibbs-Helmholtz: [∂(ΔG/T)/∂T]_P = -ΔH / T²",
                "van 't Hoff: ln(K2/K1) = -(ΔH°/R) · [1/T2 - 1/T1]"
            ),
            examTips = listOf(
                "At standard conditions, if ΔH is negative and ΔS is positive, ΔG is ALWAYS negative at all temperatures.",
                "CSIR-NET often asks for Maxwell relations: derive them quickly from dG = V dP - S dT by applying Euler's cross-derivative reciprocity."
            )
        )
    )

    val sampleMcqs: List<McqQuestion> = listOf(
        McqQuestion(
            id = "mcq_1",
            unit = "Organic Chemistry",
            question = "Which among the following annulenes is aromatic at room temperature?",
            options = listOf(
                "[8]Annulene (Cyclooctatetraene)",
                "[10]Annulene (all-cis or mono-trans)",
                "[14]Annulene ([14]annulene-1,6:8,13-dioxide / bridged)",
                "[4]Annulene (Cyclobutadiene)"
            ),
            answerIndex = 2,
            explanation = "Bridged [14]annulene maintains planarity and satisfies Hückel's 4n+2 rule (n=3, 14 π electrons) without transannular steric clash. Unbridged [8]annulene is tub-shaped (non-aromatic), and unbridged [10]annulene deviates from planarity.",
            hindiExplanation = "ब्रिज्ड [14]अन्यूलीन समतलीय रहता है और 14 π इलेक्ट्रॉनों (4n+2, n=3) के साथ एरोमैटिक नियम का पालन करता है। [8]अन्यूलीन टब आकार का होने से नॉन-एरोमैटिक है।",
            difficulty = "Part B (2M)"
        ),
        McqQuestion(
            id = "mcq_2",
            unit = "Inorganic Chemistry",
            question = "What is the CFSE (Crystal Field Stabilization Energy) for a low-spin d⁶ octahedral complex?",
            options = listOf(
                "-0.4 Δo + P",
                "-2.4 Δo + 2P",
                "-2.4 Δo + 3P",
                "-1.8 Δo + 2P"
            ),
            answerIndex = 1,
            explanation = "In low-spin d⁶ (e.g. [Co(NH3)6]³⁺, [Fe(CN)6]⁴⁻), all 6 electrons occupy t2g: t2g⁶ eg⁰. CFSE = 6 × (-0.4 Δo) + (3 - 1)P = -2.4 Δo + 2P (since free ion d⁶ has 1 pair, low spin has 3 pairs, net extra pairs = 2).",
            hindiExplanation = "लो-स्पिन d⁶ में सभी 6 इलेक्ट्रॉन t2g में होते हैं (t2g⁶ eg⁰)। CFSE = 6 × (-0.4 Δo) + 2P = -2.4 Δo + 2P।",
            difficulty = "Part B (2M)"
        ),
        McqQuestion(
            id = "mcq_3",
            unit = "Physical Chemistry",
            question = "For a second-order reaction 2A -> P, if the initial concentration [A]0 is doubled, the half-life t1/2 will:",
            options = listOf(
                "Double",
                "Remain unchanged",
                "Be halved",
                "Quadruple"
            ),
            answerIndex = 2,
            explanation = "For second order kinetics, t1/2 = 1 / (k · [A]0). Because half-life is inversely proportional to initial concentration, doubling [A]0 cuts the half-life in half.",
            hindiExplanation = "द्वितीय कोटि अभिक्रिया के लिए t1/2 = 1 / (k [A]0) होता है। प्रारंभिक सांद्रता दोगुनी करने पर अर्ध-आयु आधी (halved) हो जाएगी।",
            difficulty = "Part B (2M)"
        ),
        McqQuestion(
            id = "mcq_4",
            unit = "Spectroscopy",
            question = "In 13C DEPT-135 NMR spectroscopy, which carbon groups appear as negative (pointing downwards) signals?",
            options = listOf(
                "Methyl carbons (-CH3)",
                "Methylene carbons (-CH2-)",
                "Methine carbons (-CH-)",
                "Quaternary carbons (-C-)"
            ),
            answerIndex = 1,
            explanation = "In DEPT-135, CH3 and CH carbons appear with positive phase (upward), while CH2 carbons appear with negative phase (inverted/downward). Quaternary carbons do not appear at all.",
            hindiExplanation = "DEPT-135 एनएमआर में CH2 (मेथिलीन) कार्बन ऋणात्मक (नीचे की ओर) सिग्नल देते हैं, जबकि CH3 और CH ऊपर तथा क्वाटरनरी कार्बन लुप्त रहते हैं।",
            difficulty = "Part B (2M)"
        ),
        McqQuestion(
            id = "mcq_5",
            unit = "Pericyclic Reactions",
            question = "Thermal electrocyclic ring closure of (2E,4Z,6E)-octa-2,4,6-triene (6 π electrons) yields:",
            options = listOf(
                "cis-5,6-dimethylcyclohexa-1,3-diene via disrotatory mode",
                "trans-5,6-dimethylcyclohexa-1,3-diene via disrotatory mode",
                "cis-5,6-dimethylcyclohexa-1,3-diene via conrotatory mode",
                "trans-5,6-dimethylcyclohexa-1,3-diene via conrotatory mode"
            ),
            answerIndex = 0,
            explanation = "For a (4n+2) π system under thermal conditions (Δ), the electrocyclic mode is DISROTATORY. The terminal methyls are (E,E) (same geometry/pointing in same relative sense), so disrotatory rotation turns them into the CIS orientation.",
            hindiExplanation = "6 π इलेक्ट्रॉन प्रणाली तापीय परिस्थिति (Δ) में डिसरोटेटरी (disrotatory) मार्ग से वलय बंद करती है, जिससे cis-5,6-डाइमेथिल साइक्लोहेक्सा-1,3-डाइन प्राप्त होता है।",
            difficulty = "Part C (4M)"
        ),
        McqQuestion(
            id = "mcq_6",
            unit = "Inorganic Chemistry",
            question = "The cluster [Fe3(CO)12] obeys the 18-electron rule. The number of metal-metal (Fe-Fe) bonds in this cluster is:",
            options = listOf(
                "2",
                "3",
                "4",
                "6"
            ),
            answerIndex = 1,
            explanation = "Total Valence Electrons (TVE): 3 × Fe (8) + 12 × CO (2) = 24 + 24 = 48 e⁻. Formula for M-M bonds: (18 × n - TVE) / 2 = (18 × 3 - 48) / 2 = (54 - 48) / 2 = 6 / 2 = 3 bonds. Forms a triangular Fe3 core.",
            hindiExplanation = "कुल संयोजी इलेक्ट्रॉन = 3×8 + 12×2 = 48। M-M बंधों की संख्या = (18×3 - 48)/2 = 3। यह त्रिभुजाकार कोर बनाता है।",
            difficulty = "Part C (4M)"
        ),
        McqQuestion(
            id = "mcq_7",
            unit = "Coordination Chemistry",
            question = "Which of the following octahedral complexes is expected to exhibit strong dynamic/static Jahn-Teller distortion?",
            options = listOf(
                "[Cr(H2O)6]³⁺ (d³)",
                "[Ni(H2O)6]²⁺ (d⁸)",
                "[Cu(H2O)6]²⁺ (d⁹)",
                "[Mn(H2O)6]²⁺ (high spin d⁵)"
            ),
            answerIndex = 2,
            explanation = "Cu(II) is a d⁹ system with configuration t2g⁶ eg³. The eg orbital is asymmetrically occupied (3 electrons in 2 degenerate orbitals dx²-y² and dz²), leading to strong Jahn-Teller tetragonal distortion (z-out elongation).",
            hindiExplanation = "Cu²⁺ (d⁹) में eg कक्षक विषम रूप से भरा होता है (t2g⁶ eg³), जिसके कारण प्रबल जॉन-टेलर विकृति (Jahn-Teller distortion) उत्पन्न होती है।",
            difficulty = "Part B (2M)"
        ),
        McqQuestion(
            id = "mcq_8",
            unit = "Physical Chemistry",
            question = "According to Maxwell's thermodynamic relations, (∂S/∂V)_T is equal to:",
            options = listOf(
                "(∂P/∂T)_V",
                "-(∂P/∂T)_V",
                "(∂V/∂T)_P",
                "-(∂T/∂P)_S"
            ),
            answerIndex = 0,
            explanation = "From Helmholtz free energy dA = -S dT - P dV, cross differentiation gives (∂/∂V [-S])_T = (∂/∂T [-P])_V, which yields (∂S/∂V)_T = (∂P/∂T)_V.",
            hindiExplanation = "हेल्महोल्ट्ज़ समीकरण dA = -S dT - P dV से अवकलन करने पर (∂S/∂V)_T = (∂P/∂T)_V प्राप्त होता है।",
            difficulty = "Part B (2M)"
        )
    )

    val sampleFormulas: List<FormulaCard> = listOf(
        FormulaCard(
            id = "form_cft",
            unit = "Inorganic Chemistry",
            title = "CFSE for Octahedral Complexes",
            formula = "CFSE = [-0.4·n(t2g) + 0.6·n(eg)] Δo + m·P",
            description = "Calculates thermodynamic stabilization due to d-orbital splitting in an octahedral ligand field.",
            applicability = "High-spin vs low-spin coordination complexes, spin crossover calculations."
        ),
        FormulaCard(
            id = "form_arrhenius",
            unit = "Physical Chemistry",
            title = "Arrhenius Equation & Activation Energy",
            formula = "ln(k2 / k1) = (Ea / R) · [(1 / T1) - (1 / T2)]",
            description = "Relates rate constants at two different temperatures to activation energy Ea.",
            applicability = "Chemical kinetics, temperature dependence of rate constants."
        ),
        FormulaCard(
            id = "form_beer",
            unit = "Spectroscopy",
            title = "Beer-Lambert Law",
            formula = "A = log10(I0 / I) = ε · l · c",
            description = "Relates absorbance A to path length l (cm) and molar concentration c (mol/L).",
            applicability = "Quantitative UV-Vis spectrophotometry, concentration assays."
        ),
        FormulaCard(
            id = "form_huckel",
            unit = "Organic Chemistry",
            title = "Hückel's Rule of Aromaticity",
            formula = "π electrons = 4n + 2  (n = 0, 1, 2, 3...)",
            description = "Criterion for aromatic stabilization in planar monocyclic conjugated π systems.",
            applicability = "Annulenes, aromatic ions (Cp⁻, Tropylium, COT²⁻)."
        ),
        FormulaCard(
            id = "form_mm_bonds",
            unit = "Inorganic Chemistry",
            title = "Metal-Metal Bonds in Organometallic Clusters",
            formula = "M-M Bonds = (18 · n - TVE) / 2",
            description = "Calculates total number of metal-metal single bonds in transition metal clusters.",
            applicability = "Carbonyl clusters: Fe3(CO)12, Co4(CO)12, Rh6(CO)16."
        ),
        FormulaCard(
            id = "form_half_life",
            unit = "Physical Chemistry",
            title = "Reaction Half-Life Formulae",
            formula = "0th: t1/2 = [A]0 / 2k  |  1st: t1/2 = 0.693 / k  |  2nd: t1/2 = 1 / (k·[A]0)",
            description = "Half-life dependence on initial reactant concentration across reaction orders.",
            applicability = "Identifying reaction order from experimental half-life data."
        )
    )

    val videoPlaylists: List<VideoResource> = listOf(
        VideoResource(
            title = "CSIR-NET Organic Chemistry (Hindi + Eng)",
            subject = "Organic Chemistry",
            description = "Reaction mechanisms, stereochemistry, pericyclic reactions and reagents for CSIR-NET & GATE.",
            url = "https://www.youtube.com/results?search_query=CSIR+NET+Chemical+Science+Organic+Chemistry+Hindi+Karma",
            badge = "High Yield"
        ),
        VideoResource(
            title = "Inorganic & Coordination Chemistry Lectures",
            subject = "Inorganic Chemistry",
            description = "Crystal Field Theory, Organometallics, 18-electron rule, and Bioinorganic chemistry.",
            url = "https://www.youtube.com/results?search_query=CSIR+NET+Chemical+Science+Inorganic+Chemistry+Hindi",
            badge = "Core Unit"
        ),
        VideoResource(
            title = "Physical Chemistry & Quantum Mechanics",
            subject = "Physical Chemistry",
            description = "Chemical kinetics, thermodynamics, electrochemistry, and particle in a box problem drills.",
            url = "https://www.youtube.com/results?search_query=CSIR+NET+Chemical+Science+Physical+Chemistry+Hindi",
            badge = "Formula Based"
        ),
        VideoResource(
            title = "Spectroscopy (NMR, IR, UV-Vis, Mass)",
            subject = "Spectroscopy",
            description = "Complete spectroscopic structure elucidation problems from CSIR-NET previous years.",
            url = "https://www.youtube.com/results?search_query=CSIR+NET+Chemical+Science+Spectroscopy+Solved+Questions",
            badge = "Score Booster"
        ),
        VideoResource(
            title = "Pericyclic & Photochemistry Crash Course",
            subject = "Pericyclic Reactions",
            description = "Woodward-Hoffmann rules, electrocyclic, cycloadditions, and sigmatropic rearrangements.",
            url = "https://www.youtube.com/results?search_query=CSIR+NET+Pericyclic+Reactions+Hindi",
            badge = "Direct 4M"
        ),
        VideoResource(
            title = "CGSET & State SET Chemical Science",
            subject = "State SET Exam",
            description = "Targeted PYQ solving, syllabus breakdown and strategy for CGSET and State SET exams.",
            url = "https://www.youtube.com/results?search_query=CGSET+Chemical+Science+Chemistry+Solved+Papers",
            badge = "SET Special"
        )
    )
}
